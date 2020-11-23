package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/20 7:58 PM
 */
@Getter
@Setter
public class BusinessPopularizeConsumeVO {
    /**
     * 渠道名称
     */
    private String channel;
    /**
     * 支付次数
     */
    private Long paymentAmount;
    /**
     * 支付金额
     */
    private Double paymentSum;
    /**
     * 提现次数
     */
    private Long withdrawAmount;
    /**
     * 提现金额
     */
    private Double withdrawSum;
}
