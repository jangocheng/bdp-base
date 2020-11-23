package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

@Getter
@Setter
@Table(name = "base-bi.business_popu_stream_consume_day")
public class BusinessPopuStreamConsumeDay {

    private String id;
    private String bizDate;
    private String brandName;
    private String channel;
    private Long paymentAmount;
    private Double paymentSum;
    private String rName;
    private String storeName;
    private Long withdrawAmount;
    private Double withdrawSum;


}
