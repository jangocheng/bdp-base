package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "base-bi.business_popu_loan_period")
@Getter
@Setter
public class BusinessPopuLoanPeriod {

    private String id;
    private String bizDate;
    private String channel;
    private String customerId;
    private String summaryField;
    private String typeName;

}
