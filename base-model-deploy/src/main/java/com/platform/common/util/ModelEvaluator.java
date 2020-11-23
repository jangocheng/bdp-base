package com.platform.common.util;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SlidingWindowReservoir;
import com.codahale.metrics.Timer;
import com.platform.config.property.ModelParams;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.dmg.pmml.PMMLObject;
import org.jpmml.evaluator.*;
import org.jpmml.evaluator.visitors.ElementInternerBattery;
import org.jpmml.evaluator.visitors.ElementOptimizerBattery;
import org.jpmml.model.VisitorBattery;
import org.jpmml.model.visitors.*;

import java.io.Console;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * author: wlhbdp
 * create: 2020-05-20 17:34
 */

public class ModelEvaluator {

    private ModelParams modelParams;

    public ModelEvaluator(ModelParams modelParams) {
        this.modelParams = modelParams;
    }

    public Evaluator initEvaluator(PMML pmml, String modelName) {
        String cacheBuilderSpec = modelParams.getCacheBuilderSpec();
        if(cacheBuilderSpec != null){
            CacheBuilderSpec spec = CacheBuilderSpec.parse(cacheBuilderSpec);
            CacheUtil.setCacheBuilderSpec(spec);
        }

        VisitorBattery visitorBattery = new VisitorBattery();
        Boolean intern = modelParams.getIntern();
        if(intern){
            visitorBattery.add(LocatorNullifier.class);
        }

        // Optimize first, intern second.
        // The goal is to intern optimized elements (keeps one copy), not optimize interned elements (expands one copy to multiple copies).
        Boolean optimize = modelParams.getOptimize();
        if(optimize){
            visitorBattery.addAll(new AttributeOptimizerBattery());
            visitorBattery.addAll(new ElementOptimizerBattery());
        }

        if(intern){
            visitorBattery.addAll(new AttributeInternerBattery());
            visitorBattery.addAll(new ElementInternerBattery());
            visitorBattery.addAll(new ListFinalizerBattery());
        }

        visitorBattery.applyTo(pmml);

        Boolean measure = modelParams.getMeasure();
        if(measure){
            MemoryMeasurer memoryMeasurer = new MemoryMeasurer();
            memoryMeasurer.applyTo(pmml);

            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            numberFormat.setGroupingUsed(true);

            long size = memoryMeasurer.getSize();
            System.out.println("Bytesize of the object graph: " + numberFormat.format(size));

            Set<Object> objects = memoryMeasurer.getObjects();

            long objectCount = objects.size();

            System.out.println("Number of distinct Java objects in the object graph: " + numberFormat.format(objectCount));

            long pmmlObjectCount = objects.stream()
                    .filter(PMMLObject.class::isInstance)
                    .count();

            System.out.println("\t" + "PMML class model objects: " + numberFormat.format(pmmlObjectCount));
            System.out.println("\t" + "Other objects: " + numberFormat.format(objectCount - pmmlObjectCount));
        }

        Boolean filterOutput = modelParams.getFilterOutput();
        EvaluatorBuilder evaluatorBuilder = new ModelEvaluatorBuilder(pmml, modelName)
                .setModelEvaluatorFactory(ModelEvaluatorFactory.newInstance())
                .setValueFactoryFactory(ValueFactoryFactory.newInstance())
                .setOutputFilter(filterOutput ? OutputFilters.KEEP_FINAL_RESULTS : OutputFilters.KEEP_ALL);

        Evaluator evaluator = evaluatorBuilder.build();

        // Perform self-testing
        //The model evaluator should be verified once, before putting it into actual use.
        evaluator.verify();
        return evaluator;
    }

    public void execute(PMML pmml, String modelName) throws IOException {
        MetricRegistry metricRegistry = new MetricRegistry();

        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        FileInputStream input = new FileInputStream("");
        String separator = ",";
        CsvUtil.Table inputTable = CsvUtil.readTable(input, separator);

        List<String> missingValues = modelParams.getMissingValues();
        List<? extends Map<FieldName, ?>> inputRecords = BatchUtil.parseRecords(
                inputTable,
                FileUtils.createCellParser(missingValues.size() > 0 ? new HashSet<>(missingValues) : null));

        Evaluator evaluator = initEvaluator(pmml, modelName);

        List<InputField> inputFields = evaluator.getInputFields();
        List<InputField> groupFields = Collections.emptyList();

        if(evaluator instanceof HasGroupFields){
            HasGroupFields hasGroupfields = (HasGroupFields)evaluator;

            groupFields = hasGroupfields.getGroupFields();
        } // End if

        Boolean sparse = modelParams.getSparse();
        if(inputRecords.size() > 0){
            Map<FieldName, ?> inputRecord = inputRecords.get(0);

            Sets.SetView<FieldName> missingInputFields = Sets.difference(
                    new LinkedHashSet<>(Lists.transform(inputFields, InputField::getName)),
                    inputRecord.keySet());
            if((missingInputFields.size() > 0) && !sparse){
                throw new IllegalArgumentException("Missing input field(s): " + missingInputFields);
            }

            Sets.SetView<FieldName> missingGroupFields = Sets.difference(
                    new LinkedHashSet<>(Lists.transform(groupFields, InputField::getName)),
                    inputRecord.keySet());
            if(missingGroupFields.size() > 0){
                throw new IllegalArgumentException("Missing group field(s): " + missingGroupFields);
            }
        }

        if(evaluator instanceof HasGroupFields){
            HasGroupFields hasGroupFields = (HasGroupFields)evaluator;

            inputRecords = EvaluatorUtil.groupRows(hasGroupFields, inputRecords);
        }

        List<Map<FieldName, ?>> outputRecords = new ArrayList<>(inputRecords.size());

        Integer loop = modelParams.getLoop();
        Timer timer = new Timer(new SlidingWindowReservoir(loop));

        metricRegistry.register("main", timer);

        boolean waitBeforeLoop = false;
        if(waitBeforeLoop){
            waitForUserInput();
        }

        for(int i = 0; i < loop; i++){
            try (Timer.Context context = timer.time()) {
                outputRecords.clear();
                Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();

                for (Map<FieldName, ?> inputRecord : inputRecords) {
                    arguments.clear();
                    for (InputField inputField : inputFields) {
                        FieldName name = inputField.getName();
                        FieldValue value = inputField.prepare(inputRecord.get(name));
                        arguments.put(name, value);
                    }
                    Map<FieldName, ?> results = evaluator.evaluate(arguments);
                    outputRecords.add(results);
                }
            }
        }

        boolean waitAfterLoop = false;
        if(waitAfterLoop){
            waitForUserInput();
        }

        List<TargetField> targetFields = evaluator.getTargetFields();
        List<OutputField> outputFields = evaluator.getOutputFields();

        List<? extends ResultField> resultFields = Lists.newArrayList(Iterables.concat(targetFields, outputFields));

        CsvUtil.Table outputTable = new CsvUtil.Table();
        outputTable.setSeparator(inputTable.getSeparator());

        outputTable.addAll(
                BatchUtil.formatRecords(outputRecords,
                        new ArrayList<>(Lists.transform(resultFields, ResultField::getName)),
                        FileUtils.createCellFormatter(missingValues.size() > 0 ? missingValues.get(0) : null)));

        boolean copyColumns = true;
        if((inputTable.size() == outputTable.size()) && copyColumns){
            for(int i = 0; i < inputTable.size(); i++){
                List<String> inputRow = inputTable.get(i);
                List<String> outputRow = outputTable.get(i);

                outputRow.addAll(0, inputRow);
            }
        }

//        CsvUtil.writeTable(outputTable, this.output);

        if(loop > 1){
            reporter.report();
        }

        reporter.close();
    }

    private static void waitForUserInput(){
        Console console = System.console();
        if(console == null){
            throw new IllegalStateException();
        }

        console.readLine("Press ENTER to continue");
    }

}
