package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/19 11:47 AM
 */
@Getter
@Setter
public class BusinessConsumptionSumVO {
    /**
     * 时间
     */
    private String date;
    /**
     * 类型
     */
    private String consumptionType;
    /**
     * 金额
     */
    private Double sum;

}
