package com.platform.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.common.constant.ResponseConstants;
import com.platform.common.util.ModelHolder;
import com.platform.common.util.ResponseUtils;
import com.platform.entity.MessageTO;
import com.platform.entity.ModelRecord;
import com.platform.service.ModelLoadService;
import com.platform.service.ModelSelectionService;
import lombok.extern.slf4j.Slf4j;
import ml.combust.mleap.runtime.frame.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.dmg.pmml.PMML;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * author: wlhbdp
 * create: 2020-05-30 23:05
 */
@RestController
@RequestMapping("/model")
@Slf4j
public class CommonModelController {
    @Resource
    private ModelLoadService modelLoadService;
    @Resource
    private ModelSelectionService modelSelectionService;

    @PostMapping("/predict")
    public MessageTO getPredictionByModel(@RequestBody String content) {
        JSONObject rawData = JSON.parseObject(content);
        ModelRecord modelRecord = new ModelRecord();
        Object data = null;
        try {
            modelRecord.parseInputData(rawData);
            String modelName = modelRecord.model().modelName();
            Object model = ModelHolder.getModel(modelName);
            if (model instanceof Transformer) {
                Transformer transformer = (Transformer) model;
                data = modelLoadService.predict(modelRecord, transformer);
            }else if (model instanceof PMML){
                //

            } else {
                throw new RuntimeException("Unsupported model type!");
            }
            return ResponseUtils.responseWithSuccess(data);
        } catch (Exception e) {
            log.error("parse request data error!", e);
            return ResponseUtils.responseWithFailure(ResponseConstants.CODE_PROCESS_FAIL,e.getMessage());
        }
    }

    @PostMapping("/update")
    public MessageTO updateModel(String modelName, int modelId) {
        if (StringUtils.isBlank(modelName)) {
            return ResponseUtils.responseWithFailure("param must not be null or empty!");
        }
        try {
            modelSelectionService.updateModelByName(modelName, modelId);
            return ResponseUtils.responseWithSuccess("model has been updated successfully!");
        } catch (Exception e) {
            log.error("update model failure!", e);
            return ResponseUtils.responseWithFailure("update model failure!");
        }
    }

    @PostMapping("/reload")
    public MessageTO reloadModel(String modelName, String modelType) {
        if (StringUtils.isBlank(modelName) || StringUtils.isBlank(modelType)) {
            return ResponseUtils.responseWithFailure("param must not be null or empty!");
        }

        try {
            modelSelectionService.reloadModel(modelName, modelType);
            return ResponseUtils.responseWithSuccess("model has been reload or load successfully!");
        } catch (IOException | JAXBException | SAXException e) {
            log.error("reload model failure!", e);
            return ResponseUtils.responseWithFailure("reload or load model failure!");
        }
    }

    @PostMapping("/delete")
    public MessageTO deleteModel(String modelName) {
        if (StringUtils.isBlank(modelName)) {
            return ResponseUtils.responseWithFailure("param must not be null or empty!");
        }
        try {
            modelSelectionService.deleteModelByName(modelName);
            return ResponseUtils.responseWithSuccess("model has been deleted successfully!");
        } catch (Exception e) {
            log.error("delete model failure!", e);
            return ResponseUtils.responseWithFailure("delete model failure!");
        }
    }
}
