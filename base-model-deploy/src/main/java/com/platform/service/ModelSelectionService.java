package com.platform.service;

import ml.combust.mleap.runtime.frame.Transformer;
import org.dmg.pmml.PMML;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * author: wlhbdp
 * create: 2020-05-20 11:01
 */
public interface ModelSelectionService {

    PMML loadPMMLModelFromDB(String modelName) throws SQLException, JAXBException, SAXException, IOException;

    Map<String, Object> loadAllModelsFromDB() throws SQLException, JAXBException, SAXException, IOException;

    Transformer loadMLeapModelFromDB(String modelName) throws IOException;

    Integer updateModelByName(String modelName, int modelId);

    Integer deleteModelByName(String modelName);

    void reloadModel(String modelName, String modelType) throws IOException, JAXBException, SAXException;
}
