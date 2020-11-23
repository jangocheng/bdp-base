package com.platform.finance.report.web.mo;

import com.platform.finance.report.common.mo.PageableMO;
import com.platform.finance.report.repo.ro.FinanceResidualPrincipalDistributionReportListRO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/5 11:16
 * @description 剩余本金分布表入参
 */
@Data
public class PrincipalDistributionReportMO extends PageableMO {

    /**
     * 截止日期
     */
    @ApiModelProperty("截止日期")
    @NotBlank(message = "截止日期不为空")
    private String dataDate;

    @ApiModelProperty("明细")
    private List<FinanceResidualPrincipalDistributionReportListRO> financeResidualPrincipalDistributionReportListROList;

}
