package com.platform.finance.report.repo.ro;

import lombok.Data;

/**
 * @author wlhbdp
 * @date 2020/9/2 14:26
 * @description 实还表入参
 */
@Data
public class CumulativeActualRepaymentReportRO extends CommonRequestParamRO {

    /**
     * 产品子类型
     */
    private String productChildType;

    /**
     * 应还年月开始
     */
    private String payDateStart;

    /**
     * 应还年月结束
     */
    private String payDateEnd;

    /**
     * 还款类型
     */
    private String payType;

    /**
     * 实还年月开始
     */
    private String repayDateStart;

    /**
     * 实还年月结束
     */
    private String repayDateEnd;

    /**
     * 代偿年月开始
     */
    private String compensatoryDateStart;

    /**
     * 代偿年月结束
     */
    private String compensatoryDateEnd;

    /**
     * 买断年月开始
     */
    private String buyoutDateStart;

    /**
     * 买断年月结束
     */
    private String buyoutDateEnd;

    /**
     * 还款状态
     */
    private String payStatus;

    /**
     * 十四级分类
     */
    private String overdueGrade;
}
