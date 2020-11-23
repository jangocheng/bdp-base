package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/29 10:55
 */
@Getter
@Setter
public class BusinessApplyChainRatioVO {
    /**
     * 申请件数
     */
    private Long applyAmount;
    /**
     * 申请件数环比
     */
    private Double applyChainRatio;

    /**
     * 申请通过数
     */
    private Long applyApprovedAmount;
    /**
     * 申请通过环比
     */
    private Double applyApprovedChainRatio;
    /**
     * 申请通过率
     */
    private Double applyPassRatio;
    /**
     * 申请通过率环比
     */
    private Double applyPassRatioChainRatio;
    /**
     * 申请审核中
     */
    private Long applyApprovingAmount;
    /**
     * 申请审核中环比
     */
    private Double applyApprovingChainRatio;
    /**
     * 获批额度之和
     */
    private Double approvedCreditSum;
    /**
     * 获批额度之和环比
     */
    private Double approvedCreditSumChainRatio;
    /**
     * 时间
     */
    private String date;
}
