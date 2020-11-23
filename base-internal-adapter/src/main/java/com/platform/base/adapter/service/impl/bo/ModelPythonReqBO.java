package com.platform.base.adapter.service.impl.bo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelPythonReqBO {
    private String modelName;
    private String modelCompressWay;
    private Object data;
    private String outputType;
    private String outputName;
}
