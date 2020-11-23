package com.platform.migration.entity.vo;

public class PartitionSchemaVO {
    private String partitionName;
    private String partitionType;

    public String getPartitionName() {
        return partitionName;
    }

    public String getPartitionType() {
        return partitionType;
    }

    public void setPartitionName(String partitionName) {
        this.partitionName = partitionName;
    }

    public void setPartitionType(String partitionType) {
        this.partitionType = partitionType;
    }


    @Override
    public String toString() {
        return "PartitionSchemaVO{" +
                "partitionName='" + partitionName + '\'' +
                ", partitionType='" + partitionType + '\'' +
                '}';
    }
}
