package com.platform.finance.report.repo.ro;

import lombok.Data;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:41
 * @description 到期表入参
 */
@Data
public class CumulativeMaturityReportRO extends CommonRequestParamRO {

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
     * 还款年月开始
     */
    private String repayDateStart;

    /**
     * 还款年月结束
     */
    private String repayDateEnd;
}
