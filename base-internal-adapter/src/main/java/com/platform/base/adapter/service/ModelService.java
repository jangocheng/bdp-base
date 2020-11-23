package com.platform.base.adapter.service;

import com.platform.base.adapter.service.impl.bo.ModelPythonReqBO;

public interface ModelService {
    Object predictForFraudScore(ModelPythonReqBO ro);
}
