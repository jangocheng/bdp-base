package com.platform.finance.report.web.mo;

import com.platform.finance.report.repo.ro.FinanceOverdueReportListRO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/5 9:02
 * @description 逾期表入参
 */
@Data
public class OverdueReportMO extends CommonRequestParamMO {

    @ApiModelProperty("产品子类型")
    private String productChildType;

    @ApiModelProperty("应还年月开始")
    private String payDateStart;

    @ApiModelProperty("应还年月结束")
    private String payDateEnd;

    @ApiModelProperty("十四级分类")
    private String overdueGrade;

    @ApiModelProperty("明细")
    private List<FinanceOverdueReportListRO> financeOverdueReportListROList;
}
