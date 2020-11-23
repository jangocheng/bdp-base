package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/28 16:42
 */
@Getter
@Setter
public class GroupsSummaryDataVO {
    /**
     * 累积推送数
     */
    private Long totalCount;
    /**
     * 累积注册数
     */
    private Long totalRegister;
    /**
     * 激活薪资额度数
     */
    private Long totalActive;
    /**
     *电子钱包申请数
     */
    private Long totalCreditRegister;
    /**
     * 申请通过数
     */
    private Long totalApproved;
    /**
     * 累积推送人数
     */
    private Long totalConsumeCount;
    /**
     * 累积消费金额
     */
    private Double totalConsumeAmount;
}
