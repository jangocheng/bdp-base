package com.platform.finance.report.web.mo.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/19 16:55
 * @description
 */
@Data
public class FiveLevelReportBean {

    @ApiModelProperty("日期")
    private String dataDate;

    @ApiModelProperty("等级分类剩余本金")
    List<BigDecimal> levelAmountList;
}
