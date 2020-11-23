package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

@Getter
@Setter
@Table(name = "base-bi.business_apply_withdraw_sum")
public class BusinessApplyWithdrawSum {

    private String id;
    private String bizDate;
    private String category;
    private String channel;
    private Long totalSuccessfulAmount;
    private Double totalSuccessfulSum;
    private Long totalSuccessfulTimes;


}
