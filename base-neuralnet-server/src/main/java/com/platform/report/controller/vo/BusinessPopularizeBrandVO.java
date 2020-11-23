package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/20 7:58 PM
 */
@Getter
@Setter
public class BusinessPopularizeBrandVO {
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 消费金额
     */
    private Double consumptionSum;
}
