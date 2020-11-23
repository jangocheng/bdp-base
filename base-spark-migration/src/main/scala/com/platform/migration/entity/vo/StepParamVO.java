package com.platform.migration.entity.vo;

public class StepParamVO {
    private String paramName;
    private String paramValue;

    public String getParamName() {
        return paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "StepParamVO{" +
                "paramName='" + paramName + '\'' +
                ", paramValue='" + paramValue + '\'' +
                '}';
    }
}
