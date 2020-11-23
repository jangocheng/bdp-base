package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/26 18:41
 */
@Getter
@Setter
public class BusinessApplyReportVO {
    /**
     * 时间
     */
    private String date;
    /**
     * 通道
     */
    private String channel;
    /**
     * 系统类型
     */
    private String osType;
    /**
     * 用户类型
     */
    private String customerType;
    /**
     * 申请件数
     */
    private Long applyAmount;
    /**
     * 申请通过数
     */
    private Long applyApprovedAmount;
    /**
     * 申请取消数
     */
    private Long applyCancelledAmount;
    /**
     * 申请拒绝数
     */
    private Long applyRefusedAmount;
    /**
     * 申请审核中
     */
    private Long applyApprovingAmount;
    /**
     * 获批额度之和
     */
    private Double approvedCreditSum;
    /**
     * 待客服校验
     */
    private Long serviceValidAmount;
    /**
     * 机审通过数
     */
    private Long machinePassAmount;
    /**
     * 机审拒绝数
     */
    private Long machineRefuseAmount;
    /**
     * 退回件数
     */
    private Long applyReturnAmount;
}
