package com.platform.finance.report.repo.ro;

import lombok.Data;

/**
 * @author wlhbdp
 * @date 2020/9/2 14:41
 * @description 放款表入参
 */
@Data
public class CumulativeLoanReportRO extends CommonRequestParamRO {

    /**
     * 产品子类型
     */
    private String productChildType;

    /**
     * 借据生效年月开始
     */
    private String effectiveDateStart;

    /**
     * 借据生效年月结束
     */
    private String effectiveDateEnd;
}
