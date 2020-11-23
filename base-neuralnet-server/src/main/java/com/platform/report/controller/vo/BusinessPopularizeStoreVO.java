package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BusinessPopularizeStoreVO {
    /**
     * 商户名称
     */
    private String rName;
    /**
     * 门店名简称
     */
    private String storeName;
    /**
     * 消费次数
     */
    private Long consumptionAmount;
    /**
     * 消费金额
     */
    private Double consumptionSum;
}
