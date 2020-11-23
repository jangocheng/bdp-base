package com.platform.finance.report.web.mo;

import com.platform.finance.report.repo.ro.FinanceBuyoutCumulativePartnersReportListRO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/5 10:53
 * @description
 */
@Data
public class BuyoutReportMO extends CommonRequestParamMO {

    @ApiModelProperty("产品子类型")
    private String productChildType;

    @ApiModelProperty("买断年月开始")
    private String buyoutDateStart;

    @ApiModelProperty("买断年月结束")
    private String buyoutDateEnd;

    @ApiModelProperty("明细")
    private List<FinanceBuyoutCumulativePartnersReportListRO> financeBuyoutCumulativePartnersReportListROList;
}
