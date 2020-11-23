package com.platform.finance.report.repo.ro;

import com.platform.finance.report.common.annotation.FileNameAnnotation;
import com.platform.finance.report.common.utils.excel.annotation.ExportBeanFieldAnnotation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:38
 * @description 放款表
 */
@Data
@FileNameAnnotation(value = "放款表")
public class FinanceCumulativeLoanReportListRO {

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
     * 借据生效年月
     */
    @ExportBeanFieldAnnotation(sort = 7, title = "借据生效年月")
    @ApiModelProperty("借据生效年月")
    private String effectiveDate;

    /**
     * 期数
     */
    @ExportBeanFieldAnnotation(sort = 8, title = "期数")
    @ApiModelProperty("期数")
    private Integer term;

    /**
     * 借据本金
     */
    @ExportBeanFieldAnnotation(sort = 9, title = "借据本金")
    @ApiModelProperty("借据本金")
    private BigDecimal loanPrincipalAmount;
}
