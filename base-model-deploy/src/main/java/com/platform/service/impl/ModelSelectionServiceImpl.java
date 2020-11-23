package com.platform.service.impl;

import com.platform.common.constant.ModelTypeConstants;
import com.platform.common.util.HdfsUtils;
import com.platform.common.util.ModelHolder;
import com.platform.entity.ModelFeature;
import com.platform.entity.ModelSelection;
import com.platform.repository.ModelFeatureRepo;
import com.platform.repository.ModelSelectionRepo;
import com.platform.service.ModelLoadService;
import com.platform.service.ModelSelectionService;
import lombok.extern.slf4j.Slf4j;
import ml.combust.mleap.runtime.frame.Transformer;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.fs.FileSystem;
import org.dmg.pmml.PMML;
import org.jpmml.model.PMMLUtil;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * author: wlhbdp
 * create: 2020-05-20 11:23
 */
@Service
@Slf4j
public class ModelSelectionServiceImpl implements ModelSelectionService {
    @Resource
    private ModelSelectionRepo modelSelectionRepo;
    @Resource
    private ModelFeatureRepo modelFeatureRepo;
    @Resource
    private FileSystem fileSystem;
    @Resource
    private ModelLoadService modelLoadService;

    @Override
    public PMML loadPMMLModelFromDB(String modelName) throws JAXBException, SAXException, IOException {
        ModelSelection modelSelection = modelSelectionRepo.findByModelName(modelName);
        Integer modelId = modelSelection.getModelId();
        ModelFeature modelFeature = modelFeatureRepo.findById(modelId);
        return parsePMMLModelFromFile(modelFeature);
    }

    @Override
    public Transformer loadMLeapModelFromDB(String modelName) throws IOException {
        ModelSelection modelSelection = modelSelectionRepo.findByModelName(modelName);
        Integer modelId = modelSelection.getModelId();
        ModelFeature modelFeature = modelFeatureRepo.findById(modelId);
        return parseMLeapModelFromFile(modelFeature);
    }

    @Override
    public Map<String, Object> loadAllModelsFromDB() throws JAXBException, SAXException, IOException {
        Map<String, Object> map = new HashMap<>();
        List<ModelFeature> mleapModels = modelFeatureRepo.findByModelType("mleap");
        List<ModelFeature> pmmlModels = modelFeatureRepo.findByModelType("pmml");

        for (ModelFeature ele : mleapModels) {
            String modelName = ele.getModelName();
            map.put(modelName, parseMLeapModelFromFile(ele));
            log.info("Load model:" + modelName);
        }
        for (ModelFeature ele : pmmlModels) {
            String modelName = ele.getModelName();
            map.put(ele.getModelName(), parsePMMLModelFromFile(ele));
            log.info("Load model:" + modelName);
        }
        return map;
    }

    @Override
    public Integer updateModelByName(String modelName, int modelId) {
        return modelSelectionRepo.updateModelByName(modelName, modelId);
    }

    @Override
    public Integer deleteModelByName(String modelName) {
        return modelSelectionRepo.deleteModelByName(modelName);
    }

    @Override
    public void reloadModel(String modelName, String modelType) throws IOException, JAXBException, SAXException {
        Object model;
        if (ModelTypeConstants.MLEAP.equals(modelType)) {
            model = loadMLeapModelFromDB(modelName);
        } else if (ModelTypeConstants.PMML.equals(modelType)) {
            model = loadPMMLModelFromDB(modelName);
        } else {
            throw new RuntimeException("Unsupported model type:" + modelType);
        }
        ModelHolder.setModel(modelName, model);
    }

    private PMML parsePMMLModelFromFile(ModelFeature modelFeature) throws
            IOException, JAXBException, SAXException {
        String path = modelFeature.getPath();
        InputStream inputStream = HdfsUtils.readFile(fileSystem, path);
        PMML pmml;
        //将从数据库里读出来的数据流进行gzip解压
        try (InputStream is = new GZIPInputStream(inputStream)){
            pmml = PMMLUtil.unmarshal(is);
        } catch (IOException e) {
            log.error("Transfer data from blob to InputStream error.", e);
            throw e;
        } catch (JAXBException | SAXException e) {
            log.error("Parse pmml model failure.", e);
            throw e;
        }
        return pmml;
    }

    /**
     * Read model data from hdfs ,write it to local file system and return local file path
     * @param modelFeature modelFeature
     * @return Transformer
     * @throws IOException IOException
     */
    private Transformer parseMLeapModelForFile(ModelFeature modelFeature) throws IOException {
        String path = modelFeature.getPath();
        String[] splits = path.split("/");
        String fileName = splits[splits.length-1];
        InputStream is = HdfsUtils.readFile(fileSystem, path);
        String modelPath = "/tmp/model";
        File modelDir = new File(modelPath);
        if (! modelDir.exists()) {
            boolean isOK = modelDir.mkdir();
            if (! isOK)
                throw new RuntimeException("Create new directory [" + modelPath + "] failure!");
        }
        String localPath = modelPath + File.separator + fileName;
        try (OutputStream os = new FileOutputStream(localPath)){
            IOUtils.copy(is, os);
            log.info("Successfully copy data from hdfs to local directory!");
        }
        localPath = "jar:file:" + localPath;
        System.out.println("model local path: " + localPath);
        return modelLoadService.loadModelFromFile(localPath);
    }

    private Transformer parseMLeapModelFromFile(ModelFeature modelFeature) {
        String path = modelFeature.getPath();
        return modelLoadService.loadXgBoostModelFromHDFS(path, fileSystem);
    }

}
