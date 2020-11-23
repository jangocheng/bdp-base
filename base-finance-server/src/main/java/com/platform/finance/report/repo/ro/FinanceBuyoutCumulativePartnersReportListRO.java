package com.platform.finance.report.repo.ro;

import com.platform.finance.report.common.annotation.FileNameAnnotation;
import com.platform.finance.report.common.utils.excel.annotation.ExportBeanFieldAnnotation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:38
 * @description 买断表
 */
@Data
@FileNameAnnotation(value = "买断表")
public class FinanceBuyoutCumulativePartnersReportListRO {

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
     * 产品类型
     */
    @ExportBeanFieldAnnotation(sort = 4, title = "产品类型")
    @ApiModelProperty("产品类型")
    private String productType;

    /**
     * 买断年月
     */
    @ExportBeanFieldAnnotation(sort = 5, title = "买断年月")
    @ApiModelProperty("买断年月")
    private String buyOutDate;

    /**
     * 本金
     */
    @ExportBeanFieldAnnotation(sort = 6, title = "本金")
    @ApiModelProperty("本金")
    private BigDecimal principalAmount;

    /**
     * 利息
     */
    @ExportBeanFieldAnnotation(sort = 7, title = "利息")
    @ApiModelProperty("利息")
    private BigDecimal interestAmount;

    /**
     * 罚息
     */
    @ExportBeanFieldAnnotation(sort = 8, title = "罚息")
    @ApiModelProperty("罚息")
    private BigDecimal penaltyAmount;

    /**
     * 服务费
     */
    @ExportBeanFieldAnnotation(sort = 9, title = "服务费")
    @ApiModelProperty("服务费")
    private BigDecimal serviceAmount;

    /**
     * 合计
     */
    @ExportBeanFieldAnnotation(sort = 10, title = "合计")
    @ApiModelProperty("合计")
    private BigDecimal totalAmount;
}
