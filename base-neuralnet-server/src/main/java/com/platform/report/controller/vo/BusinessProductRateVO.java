package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class BusinessProductRateVO {
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 金额
     */
    private Double consumptionSum;
}
