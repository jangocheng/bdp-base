package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/08 2:00 PM
 */
@Getter
@Setter
public class RealTimeConsumptionVO {
    /**
     * 每小时对应的量
     */
    private Double withdraw;
    private Double creditPayment;
    private Double salaryPayment;
    /**
     * 当前累计量
     */
    private Double withdrawSum;
    private Double creditPaymentSum;
    private Double salaryPaymentSum;
    private Integer bizHour;

}
