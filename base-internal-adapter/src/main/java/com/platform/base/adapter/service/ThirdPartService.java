package com.platform.base.adapter.service;

import com.platform.base.adapter.service.impl.bo.ModelPythonReqBO;
import com.platform.base.adapter.web.controller.mo.PredictionReqMO;

public interface ThirdPartService {
    ModelPythonReqBO assembleDataForFraudModel(PredictionReqMO identityCard);
}
