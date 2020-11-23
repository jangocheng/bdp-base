package com.platform.finance.report.repo.ro;

import lombok.Data;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:41
 * @description 买断表
 */
@Data
public class BuyoutCumulativePartnersReportRO extends CommonRequestParamRO {

    /**
     * 买断年月开始
     */
    private String buyoutDateStart;

    /**
     * 买断年月结束
     */
    private String buyoutDateEnd;
}
