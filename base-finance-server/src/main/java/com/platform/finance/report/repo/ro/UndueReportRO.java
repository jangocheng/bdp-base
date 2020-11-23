package com.platform.finance.report.repo.ro;

import lombok.Data;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:08
 * @description
 */
@Data
public class UndueReportRO extends CommonRequestParamRO {

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
}
