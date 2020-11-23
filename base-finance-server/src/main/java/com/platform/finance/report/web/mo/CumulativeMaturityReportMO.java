package com.platform.finance.report.web.mo;

import com.platform.finance.report.repo.ro.FinanceCumulativeMaturityReportListRO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 18:27
 * @description 到期表入参
 */
@Data
public class CumulativeMaturityReportMO extends CommonRequestParamMO {

    @ApiModelProperty("产品子类型")
    private String productChildType;

    @ApiModelProperty("应还年月开始")
    private String payDateStart;

    @ApiModelProperty("应还年月结束")
    private String payDateEnd;

    @ApiModelProperty("还款类型")
    private String payType;

    @ApiModelProperty("还款年月开始")
    private String repayDateStart;

    @ApiModelProperty("还款年月结束")
    private String repayDateEnd;

    @ApiModelProperty("明细")
    private List<FinanceCumulativeMaturityReportListRO> financeCumulativeMaturityReportListROList;
}
