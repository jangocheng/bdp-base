package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/29 10:55
 */
@Getter
@Setter
public class BusinessRegisterChainRatioVO {
    /**
     * 注册量
     */
    private Long registerAmount;
    /**
     * 注册量环比
     */
    private Double registerChainRatio;
    /**
     * 实名人数
     */
    private Long identifiedAmount;
    /**
     * 实名人数环比
     */
    private Double identifiedChainRatio;
    /**
     * 实名认证率
     */
    private Double identifiedRatio;
    /**
     * 实名认证率环比
     */
    private Double identifiedRatioChainRatio;

    /**
     * 申请总件数
     */
    private Long applyAmount;
    /**
     * 申请总件数环比
     */
    private Double applyChainRatio;
    /**
     * 申请通过率
     */
    private Double applyPassRatio;
    /**
     * 申请通过率环比
     */
    private Double applyPassRatioChainRatio;
    /**
     * 时间
     */
    private String date;
}
