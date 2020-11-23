package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/29 10:55
 */
@Getter
@Setter
public class BusinessPopularizeChainRatioVO {
    /**
     * 注册人数
     */
    private Long registerAmount;
    /**
     * 注册人数环比
     */
    private Double registerChainRatio;
    /**
     * 实名认证数环比
     */
    private Long registerIdentifiedAmount;
    /**
     * 实名认证数环比
     */
    private Double registerIdentifiedChainRatio;
    /**
     * 申请进件人数
     */
    private Long appliedAmount;
    /**
     * 申请进件人数环比
     */
    private Double appliedChainRatio;
    /**
     * 申请进件通过人数
     */
    private Long applyPassAmount;
    /**
     * 申请进件通过人数环比
     */
    private Double applyPassChainRatio;

    /**
     * 机审拒绝人数
     */
    private Long machineRefuseAmount;
    /**
     * 机审拒绝人数环比
     */
    private Double machineRefuseChainRatio;
    /**
     * 提现成功人数
     */
    private Long withdrawPassAmount;
    /**
     * 提现成功人数环比
     */
    private Double withdrawPassChainRatio;
    /**
     * 时间
     */
    private String date;
}
