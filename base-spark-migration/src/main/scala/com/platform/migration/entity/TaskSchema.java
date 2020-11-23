package com.platform.migration.entity;

import com.platform.migration.entity.vo.PartitionSchemaVO;
import com.platform.migration.entity.vo.StepSchemaVO;
import com.platform.migration.entity.vo.TbSettingSchemaVO;

import java.util.List;

public class TaskSchema {
    private String taskName;
    private String tableName;
    private boolean hasIncrement;
    private String saveDB;
    private String dbType;
    private List<PartitionSchemaVO> partitions;
    private TbSettingSchemaVO settings;
    private String tableSchema;
    private List<StepSchemaVO> steps;


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTableName() {
        return tableName;
    }

    public boolean isHasIncrement() {
        return hasIncrement;
    }

    public String getSaveDB() {
        return saveDB;
    }

    public String getDbType() {
        return dbType;
    }

    public List<PartitionSchemaVO> getPartitions() {
        return partitions;
    }

    public TbSettingSchemaVO getSettings() {
        return settings;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public List<StepSchemaVO> getSteps() {
        return steps;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setHasIncrement(boolean hasIncrement) {
        this.hasIncrement = hasIncrement;
    }

    public void setSaveDB(String saveDB) {
        this.saveDB = saveDB;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public void setPartitions(List<PartitionSchemaVO> partitions) {
        this.partitions = partitions;
    }

    public void setSettings(TbSettingSchemaVO settings) {
        this.settings = settings;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public void setSteps(List<StepSchemaVO> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "TaskSchema{" +
                "taskName='" + taskName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", hasIncrement=" + hasIncrement +
                ", saveDB='" + saveDB + '\'' +
                ", dbType='" + dbType + '\'' +
                ", partitions=" + partitions +
                ", settings=" + settings +
                ", tableSchema='" + tableSchema + '\'' +
                ", steps=" + steps +
                '}';
    }
}
