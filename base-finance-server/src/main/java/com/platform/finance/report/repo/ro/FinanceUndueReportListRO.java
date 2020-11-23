package com.platform.finance.report.repo.ro;

import com.platform.finance.report.common.annotation.FileNameAnnotation;
import com.platform.finance.report.common.utils.excel.annotation.ExportBeanFieldAnnotation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:15
 * @description 未到期表
 */
@Data
@FileNameAnnotation(value = "未到期表")
public class FinanceUndueReportListRO {

    /**
     * 资金方
     */
    @ExportBeanFieldAnnotation(sort = 1, title = "资金方")
    @ApiModelProperty("资金方")
    private String fundingParty;

    /**
     * 资产渠道类型
     */
    @ExportBeanFieldAnnotation(sort = 2, title = "资产渠道类型")
    @ApiModelProperty("资产渠道类型")
    private String channelType;

    /**
     * 渠道名称
     */
    @ExportBeanFieldAnnotation(sort = 3, title = "渠道名称")
    @ApiModelProperty("渠道名称")
    private String channelName;

    /**
     * 是否引流项目
     */
    @ExportBeanFieldAnnotation(sort = 4, title = "是否引流项目")
    @ApiModelProperty("是否引流项目")
    private String isDrainage;

    /**
     * 产品类型
     */
    @ExportBeanFieldAnnotation(sort = 5, title = "产品类型")
    @ApiModelProperty("产品类型")
    private String productType;

    /**
     * 产品子类型
     */
    @ExportBeanFieldAnnotation(sort = 6, title = "产品子类型")
    @ApiModelProperty("产品子类型")
    private String productChildType;

    /**
     * 应还年月
     */
    @ExportBeanFieldAnnotation(sort = 7, title = "应还年月")
    @ApiModelProperty("应还年月")
    private String payDate;

    /**
     * 未到期本金
     */
    @ExportBeanFieldAnnotation(sort = 8, title = "未到期本金")
    @ApiModelProperty("未到期本金")
    private BigDecimal principalAmount;

    /**
     * 未到期利息
     */
    @ExportBeanFieldAnnotation(sort = 9, title = "未到期利息")
    @ApiModelProperty("未到期利息")
    private BigDecimal interestAmount;

    /**
     * 未到期服务费
     */
    @ExportBeanFieldAnnotation(sort = 10, title = "未到期服务费")
    @ApiModelProperty("未到期服务费")
    private BigDecimal serviceAmount;
}
