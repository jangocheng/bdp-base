package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/19 11:47 AM
 */
@Getter
@Setter
public class BusinessConsumptionAmountVO {
    /**
     * 时间
     */
    private String date;
    /**
     * 类型
     */
    private String consumptionType;
    /**
     * 数量
     */
    private Long amount;

}
