package com.platform.finance.report.web.mo;

import com.platform.finance.report.repo.ro.FinanceCumulativeActualRepaymentReportListRO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 18:59
 * @description 实还表入参
 */
@Data
public class CumulativeActualRepaymentReportMO extends CommonRequestParamMO {

    @ApiModelProperty("产品子类型")
    private String productChildType;

    @ApiModelProperty("应还年月开始")
    private String payDateStart;

    @ApiModelProperty("应还年月结束")
    private String payDateEnd;

    @ApiModelProperty("还款类型")
    private String payType;

    @ApiModelProperty("实还年月开始")
    private String repayDateStart;

    @ApiModelProperty("实还年月结束")
    private String repayDateEnd;

    @ApiModelProperty("代偿年月开始")
    private String compensatoryDateStart;

    @ApiModelProperty("代偿年月结束")
    private String compensatoryDateEnd;

    @ApiModelProperty("买断年月开始")
    private String buyoutDateStart;

    @ApiModelProperty("买断年月结束")
    private String buyoutDateEnd;

    @ApiModelProperty("还款状态")
    private String payStatus;

    @ApiModelProperty("十四级分类")
    private String overdueGrade;

    @ApiModelProperty("明细")
    private List<FinanceCumulativeActualRepaymentReportListRO> financeCumulativeActualRepaymentReportListROList;
}
