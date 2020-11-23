package com.platform.finance.report.repo.ro;

import lombok.Data;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:08
 * @description 减免表
 */
@Data
public class CumulativeDeductionReportRO extends CommonRequestParamRO {

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
     * 减免类型
     */
    private String deductionType;

    /**
     * 减免年月开始
     */
    private String deductionDateStart;

    /**
     * 减免年月结束
     */
    private String deductionDateEnd;

}
