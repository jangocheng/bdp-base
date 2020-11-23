package com.platform.finance.report.web.mo;

import com.platform.finance.report.repo.ro.FinanceCumulativeDeductionReportListRO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 19:06
 * @description 减免表入参
 */
@Data
public class CumulativeDeductionReportMO extends CommonRequestParamMO {

    @ApiModelProperty("产品子类型")
    private String productChildType;

    @ApiModelProperty("应还年月开始")
    private String payDateStart;

    @ApiModelProperty("应还年月结束")
    private String payDateEnd;

    @ApiModelProperty("减免类型")
    private String deductionType;

    @ApiModelProperty("减免年月开始")
    private String deductionDateStart;

    @ApiModelProperty("减免年月结束")
    private String deductionDateEnd;

    @ApiModelProperty("明细")
    private List<FinanceCumulativeDeductionReportListRO> financeCumulativeDeductionReportListROList;

}
