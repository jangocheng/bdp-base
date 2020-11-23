package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;


@Getter
@Setter
@Table(name = "base-bi.business_popu_channel_day")
public class BusinessPopuChannelDay {

    private String id;
    private Long appliedAmount;
    private Long applyPass;
    private Long applyRefuse;
    private Double approvalSum;
    private Double approvedWithdrawSum;
    private String bizDate;
    private String channel;
    private Long identifiedAmount;
    private Long machinePass;
    private Long machineRefuse;
    private Long registerAmount;
    private Long repaymentWithdrawAmount;
    private Long withdrawAmount;
    private Long withdrawTimes;
    private Double withdrawSum;

}
