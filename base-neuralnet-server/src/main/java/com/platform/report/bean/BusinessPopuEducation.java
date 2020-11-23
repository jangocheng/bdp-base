package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "base-bi.business_popu_education")
@Getter
@Setter
public class BusinessPopuEducation {

    private String id;
    private String bizDate;
    private String channel;
    private String customerId;
    private String summaryField;
    private String type;
    private String typeName;
}
