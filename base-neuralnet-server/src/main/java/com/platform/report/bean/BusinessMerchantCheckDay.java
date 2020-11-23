package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "base-bi.business_merchant_check_day")
@Getter
@Setter
public class BusinessMerchantCheckDay {

    private String id;
    private Long accessAmount;
    private Long accessApplyAmount;
    private Long accessFirstTrialAmount;
    private Long activationAmount;
    private Long applyAmount;
    private Long applyApprovedAmount;
    private Long approvalRefuseAmount;
    private String bizDate;
    private Long closeAmount;
    private Long firstTrialAmount;
    private Long newAmount;
    private Long refuseAmount;
    private Long returnSupplementaryAmount;

}
