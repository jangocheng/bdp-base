package com.platform.common.util;

import org.apache.commons.io.IOUtils;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.EvaluatorUtil;
import org.jpmml.model.PMMLUtil;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class FileUtils {
    public static PMML readPMML(File file) throws Exception {
        try(InputStream is = new FileInputStream(file)){
            return PMMLUtil.unmarshal(is);
        }
    }

    public static void writePMML(PMML pmml, File file) throws Exception {
        try(OutputStream os = new FileOutputStream(file)){
            PMMLUtil.marshal(pmml, os);
        }
    }

    public static CsvUtil.Table readTable(File file, String separator) throws IOException {
        try(InputStream is = new FileInputStream(file)){
            return CsvUtil.readTable(is, separator);
        }
    }

    public static void writeTable(CsvUtil.Table table, File file) throws IOException {
        try(OutputStream os = new FileOutputStream(file)){
            CsvUtil.writeTable(table, os);
        }
    }

    public static Object newInstance(Class<?> clazz) throws ReflectiveOperationException {
        Method newInstanceMethod = clazz.getDeclaredMethod("newInstance");

        return newInstanceMethod.invoke(null);
    }

    public static final Function<String, String> CSV_PARSER = FileUtils::getString;

    public static final Function<Object, String> CSV_FORMATTER = object -> {
        object = EvaluatorUtil.decode(object);

        if(object == null){
            return "N/A";
        }

        return object.toString();
    };

    private static String stripQuotes(String rawString, char quoteChar){

        if(rawString.length() > 1 && ((rawString.charAt(0) == quoteChar) && (rawString.charAt(rawString.length() - 1) == quoteChar))){
            return rawString.substring(1, rawString.length() - 1);
        }

        return rawString;
    }

    public static String getString(String rawStr) {
        if(("").equals(rawStr) || ("N/A").equals(rawStr) || ("NA").equals(rawStr)){
            return null;
        }
        return processString(rawStr, stripQuotes(rawStr, '\"'));

    }

    public static String processString(String rawStr, String s) {
        rawStr = s;

        // Standardize European-style decimal marks (',') to US-style decimal marks ('.')
        if (rawStr.indexOf(',') > -1) {
            String usString = rawStr.replace(',', '.');

            try {
                Double.parseDouble(usString);
                rawStr = usString;
            } catch (NumberFormatException nfe) {
                // Ignored
            }
        }

        return rawStr;
    }

    public static String[] readCodeModel(String filePath, String split) throws IOException {
        InputStream is = new FileInputStream(filePath);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line ;
            StringBuilder sb = new StringBuilder();
            while ((line=reader.readLine()) != null) {
                sb.append(line.trim());
            }
            return sb.toString().split(split);
        }
    }

//    public static Map<String, Map<String, Integer>> generateOneHotCodeModel(String[] rawCode) {
//        Map<String, Map<String, Integer>> map = new LinkedHashMap<>();
//        for (int i=1;i<rawCode.length;i++) {
//            Map<String, Integer> innerMap = new LinkedHashMap<>();
//            FeatureVO featureVO = JSON.parseObject(rawCode[i], FeatureVO.class);
//            Integer[] newFeatIds = featureVO.getNewFeatId();
//            String[] newValues = featureVO.getNewValues();
//            for (int index=0;index<newFeatIds.length;index++) {
//                innerMap.put(newValues[index], newFeatIds[index]);
//            }
//            map.put(featureVO.getFeatureName(), innerMap);
//        }
//        return map;
//    }


    static
    public Function<String, String> createCellParser(Collection<String> missingValues){
        Function<String, String> function = new Function<String, String>(){

            @Override
            public String apply(String string){

                if(missingValues != null && missingValues.contains(string)){
                    return null;
                }

                // Remove leading and trailing quotation marks
                return processString(string, stripQuotes(string, '\"'));
            }

            private String stripQuotes(String string, char quoteChar){

                if(string.length() > 1 && ((string.charAt(0) == quoteChar) && (string.charAt(string.length() - 1) == quoteChar))){
                    return string.substring(1, string.length() - 1);
                }

                return string;
            }
        };

        return function;
    }

    public static Function<Object, String> createCellFormatter(String missingValue){
        Function<Object, String> function = object -> {
            object = EvaluatorUtil.decode(object);

            if(object == null){
                return missingValue;
            }

            return object.toString();
        };

        return function;
    }

    public static String readFileFromResource(String path) throws IOException {
        InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(path);
        if (is == null)
            throw new RuntimeException("Read file failure! file path: " + path);
        List<String> lines = IOUtils.readLines(is);
        StringBuilder sb = new StringBuilder();
        lines.forEach(sb::append);
        return sb.toString();
    }

}
