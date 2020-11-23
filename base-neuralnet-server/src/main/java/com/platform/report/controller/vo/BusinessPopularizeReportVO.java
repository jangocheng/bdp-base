package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessPopularizeReportVO {
    /**
     * 时间
     */
    private String date;

    /**
     * 渠道名称
     */
    private String channel;
    /**
     * 终端
     */
    private String osType;
    /**
     * 注册人数
     */
    private Long registerAmount;
    /**
     * 实名认证数
     */
    private Long registerIdentifiedAmount;
    /**
     * 申请进件人数
     */
    private Long applyAmount;
    /**
     * 申请进件通过人数
     */
    private Long applyApprovedAmount;
    /**
     * 申请拒绝人数
     */
    private Long applyRefuseAmount;
    /**
     * 机审拒绝人数
     */
    private Long machineRefuseAmount;
    /**
     * 提现成功人数
     */
    private Long withdrawPassAmount;
}
