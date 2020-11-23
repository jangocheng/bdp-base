package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/19 2:56 PM
 */
@Getter
@Setter
public class BusinessStoreCheckVO {

    /**
     * eg: 20180910, 时间
     */
    private String date;
    /**
     * eg: 1000,  申请件数
     */
    private Long applyAmount;
    /**
     * eg: 300,   申请通过数
     */
    private Long applyApprovedAmount;
    /**
     * eg: 100,        准入审核-初审数
     */
    private Long accessFirstTrialAmount;
    /**
     * eg: 600,     退回补充资料数
     */
    private Long returnSupplementaryAmount;
    /**
     * eg:3432 ,       准入申请
     */
    private Long accessApplyAmount;
    /**
     * eg: 20,   审批拒绝数
     */
    private Long approvalRefuseAmount;
    /**
     * eg: 1000283.23,   初审数
     */
    private Long firstTrialAmount;
    /**
     * eg: 1233.33,  关闭数
     */
    private Long closeAmount;
    /**
     * eg: 80,       激活数
     */
    private Long activationAmount;
    /**
     * eg:124324.22,拒绝数
     */
    private Long refuseAmount;
    /**
     * eg:124324,新增数
     */
    private Long newAmount;
    /**
     * eg:124324.344,准入数
     */
    private Long accessAmount;
    /**
     * eg: 1000, 积累申请件数
     */
    private Long applyTotalAmount;
    /**
     * eg: 300,  累计申请通过数
     */
    private Long applyApprovedTotalAmount;
    /**
     * eg: 100,       累计准入审核-初审数
     */
    private Long accessFirstTrialTotalAmount;
    /**
     * eg: 600,        积累退回补充资料数
     */
    private Long returnSupplementaryTotalAmount;
    /**
     * eg:3432 ,       积累准入申请
     */
    private Long accessApplyTotalAmount;
    /**
     * eg: 20,  积累审批拒绝数
     */
    private Long approvalRefuseTotalAmount;
    /**
     * eg: 1000283.23,  积累初审数
     */
    private Long firstTrialTotalAmount;
    /**
     * eg:  1233.33,  积累关闭数
     */
    private Long closeTotalAmount;
    /**
     * eg: 80,      积累激活数
     */
    private Long activationTotalAmount;
    /**
     * eg:124324.22,积累拒绝数
     */
    private Long refuseTotalAmount;
    /**
     * eg:124324,积累新增数
     */
    private Long newTotalAmount;
    /**
     * eg:124324.344,积累准入数
     */
    private Long accessTotalAmount;
}