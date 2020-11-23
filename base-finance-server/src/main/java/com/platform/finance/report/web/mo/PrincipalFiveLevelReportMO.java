package com.platform.finance.report.web.mo;

import com.platform.finance.report.web.mo.bean.FiveLevelReportBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/5 11:35
 * @description 剩余本金五级分类表入参
 */
@Data
public class PrincipalFiveLevelReportMO{

    /**
     * 截止开始日期
     */
    @ApiModelProperty("截止开始日期")
    @NotBlank(message = "截止开始日期不为空")
    private String dataDateStart;

    /**
     * 截止结束日期
     */
    @ApiModelProperty("截止结束日期")
    @NotBlank(message = "截止结束日期不为空")
    private String dataDateEnd;

    @ApiModelProperty("明细")
    private List<FiveLevelReportBean> fiveLevelReportBeanList;
}
