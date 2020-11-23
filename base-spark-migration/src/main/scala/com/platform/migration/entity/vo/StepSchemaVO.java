package com.platform.migration.entity.vo;


import java.util.List;

public class StepSchemaVO {
    private String sql;
    private String tempView;
    private String dbType;
    private List<StepParamVO> params;

    public String getSql() {
        return sql;
    }

    public String getTempView() {
        return tempView;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public List<StepParamVO> getParams() {
        return params;
    }

    public void setParams(List<StepParamVO> params) {
        this.params = params;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setTempView(String tempView) {
        this.tempView = tempView;
    }

    @Override
    public String toString() {
        return "StepSchemaVO{" +
                "sql='" + sql + '\'' +
                ", tempView='" + tempView + '\'' +
                ", dbType='" + dbType + '\'' +
                ", params=" + params +
                '}';
    }
}
