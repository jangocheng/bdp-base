package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "base-bi.business_consumption_info_day")
@Getter
@Setter
public class BusinessConsumptionInfoDay {

    private String id;
    private Double avgConsumptionSum;
    private String bizDate;
    private Long consumptionAmount;
    private Double consumptionSum;
    private String consumptionType;
    private String customerType;
    private String customerTypeName;
    private Double maxConsumptionSum;

}
