package com.platform.finance.report.repo.ro;

import com.platform.finance.report.common.annotation.FileNameAnnotation;
import com.platform.finance.report.common.utils.excel.annotation.ExportBeanFieldAnnotation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:38
 * @description 减免表
 */
@Data
@FileNameAnnotation(value="减免表")
public class FinanceCumulativeDeductionReportListRO {

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
     * 减免类型
     */
    @ExportBeanFieldAnnotation(sort = 8, title = "减免类型")
    @ApiModelProperty("减免类型")
    private String deductionType;

    /**
     * 减免年月
     */
    @ExportBeanFieldAnnotation(sort = 9, title = "减免年月")
    @ApiModelProperty("减免年月")
    private String deductionDate;

    /**
     * 减免利息
     */
    @ExportBeanFieldAnnotation(sort = 10, title = "减免利息")
    @ApiModelProperty("减免利息")
    private BigDecimal deductionInterest;

    /**
     * 减免服务费
     */
    @ExportBeanFieldAnnotation(sort = 11, title = "减免服务费")
    @ApiModelProperty("减免服务费")
    private BigDecimal deductionService;

    /**
     * 减免扣款失败费
     */
    @ExportBeanFieldAnnotation(sort = 12, title = "减免扣款失败费")
    @ApiModelProperty("减免扣款失败费")
    private BigDecimal deductionFailAmount;

    /**
     * 减免罚息
     */
    @ExportBeanFieldAnnotation(sort = 13, title = "减免罚息")
    @ApiModelProperty("减免罚息")
    private BigDecimal deductionPenalty;

    /**
     * 减免总金额
     */
    @ExportBeanFieldAnnotation(sort = 14, title = "减免总金额")
    @ApiModelProperty("减免总金额")
    private BigDecimal deductionTotalAmount;
}
