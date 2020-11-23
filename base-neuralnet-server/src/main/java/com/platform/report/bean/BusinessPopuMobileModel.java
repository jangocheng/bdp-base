package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "base-bi.business_popu_mobile_model")
@Getter
@Setter
public class BusinessPopuMobileModel {

    private String id;
    private String bizDate;
    private String channel;
    private String summaryField;
    private String typeName;
    private String userId;

}
