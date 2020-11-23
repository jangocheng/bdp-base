package com.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.common.util.FileUtils;
import com.platform.entity.ModelRecord;
import com.platform.service.ModelLoadService;
import com.platform.service.ModelSelectionService;
import ml.combust.mleap.runtime.frame.Row;
import ml.combust.mleap.runtime.frame.Transformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import scala.collection.Iterator;
import scala.collection.Seq;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.List;

/**
 * author: wlhbdp
 * create: 2020-05-29 21:01
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ModelSelectionServiceImplTest {
    @Resource
    private ModelLoadService modelLoadService;
    @Resource
    private ModelSelectionService modelSelectionService;

    @Test
    public void testPredict() {
        String modelName = "airbnb_rf_model";
        try {
            String fileContent = FileUtils.readFileFromResource("frame.airbnb.json");
            JSONObject rawData = JSON.parseObject(fileContent);
            ModelRecord modelRecord = new ModelRecord();
            modelRecord.parseInputData(rawData);
            Transformer transformer = modelSelectionService.loadMLeapModelFromDB(modelName);
            List<Object> list = modelLoadService.predict(modelRecord, transformer);
            for (Object o : list) {
                System.out.println(o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}