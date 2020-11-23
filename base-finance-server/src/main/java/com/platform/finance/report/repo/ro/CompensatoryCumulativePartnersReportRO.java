package com.platform.finance.report.repo.ro;

import lombok.Data;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:41
 * @description 代偿表
 */
@Data
public class CompensatoryCumulativePartnersReportRO extends CommonRequestParamRO {

    /**
     * 代偿年月开始
     */
    private String compensatoryDateStart;

    /**
     * 代偿年月结束
     */
    private String compensatoryDateEnd;
}
