package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "base-bi.business_popu_gender")
@Getter
@Setter
public class BusinessPopuGender {

    private String id;
    private String bizDate;
    private String cardId;
    private String channel;
    private String summaryField;
    private String typeName;

}
