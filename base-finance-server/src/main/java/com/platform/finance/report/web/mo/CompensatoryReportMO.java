package com.platform.finance.report.web.mo;

import com.platform.finance.report.repo.ro.FinanceCompensatoryCumulativePartnersReportListRO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/5 9:56
 * @description 代偿表入参
 */
@Data
public class CompensatoryReportMO extends CommonRequestParamMO {

    @ApiModelProperty("产品子类型")
    private String productChildType;

    @ApiModelProperty("代偿年月开始")
    private String compensatoryDateStart;

    @ApiModelProperty("代偿年月结束")
    private String compensatoryDateEnd;

    @ApiModelProperty("明细")
    private List<FinanceCompensatoryCumulativePartnersReportListRO> financeCompensatoryCumulativePartnersReportListROList;
}
