package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessRegisterReportVO {
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
     * 注册量
     */
    private Long registerAmount;
    /**
     * 实名人数
     */
    private Long identifiedAmount;
    /**
     * 申请总件数
     */
    private Long applyAmount;
    /**
     * 申请通过件数
     */
    private Long applyApprovedAmount;
    /**
     * 申请拒件
     */
    private Long applyRefusedAmount;
    /**
     * 申请取消
     */
    private Long applyCancelledAmount;
    /**
     * 申请审核中
     */
    private Long applyApprovingAmount;
}
