package com.platform.finance.report.web.mo;

import com.platform.finance.report.repo.ro.FinanceCumulativeLoanReportListRO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 17:40
 * @description 放款表入参
 */
@Data
public class CumulativeLoanReportMO extends CommonRequestParamMO {

    @ApiModelProperty("产品子类型")
    private String productChildType;

    @ApiModelProperty("借据生效年月开始")
    private String effectiveDateStart;

    @ApiModelProperty("借据生效年月结束")
    private String effectiveDateEnd;

    @ApiModelProperty("明细")
    private List<FinanceCumulativeLoanReportListRO> financeCumulativeLoanReportListROList;
}
