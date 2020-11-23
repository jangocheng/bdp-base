package com.platform.migration.entity.vo;


public class TbSettingSchemaVO {
    private String storedType;
    private String tbProperties;
    private String rowFormat;

    public String getStoredType() {
        return storedType;
    }

    public String getTbProperties() {
        return tbProperties;
    }

    public String getRowFormat() {
        return rowFormat;
    }

    public void setStoredType(String storedType) {
        this.storedType = storedType;
    }

    public void setTbProperties(String tbProperties) {
        this.tbProperties = tbProperties;
    }

    public void setRowFormat(String rowFormat) {
        this.rowFormat = rowFormat;
    }

    @Override
    public String toString() {
        return "TbSettingSchemaVO{" +
                "storedType='" + storedType + '\'' +
                ", tbProperties='" + tbProperties + '\'' +
                ", rowFormat='" + rowFormat + '\'' +
                '}';
    }
}
