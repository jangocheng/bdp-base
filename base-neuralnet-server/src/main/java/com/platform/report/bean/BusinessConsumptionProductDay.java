package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "base-bi.business_consumption_product_day")
@Getter
@Setter
public class BusinessConsumptionProductDay {

    private String id;
    private String bizDate;
    private Double consumptionSum;
    private String consumptionType;
    private String customerType;
    private String customerTypeName;
    private String productType;

}
