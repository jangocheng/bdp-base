package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wlhbdp
 * @create 2020/03/19 1:56 PM
 */
@Getter
@Setter
@ToString
public class BusinessConsumptionRateVO {
    /**
     * 消费类型
     */
    private String consumptionType;
    /**
     * 金额
     */
    private Double consumptionSum;
}
