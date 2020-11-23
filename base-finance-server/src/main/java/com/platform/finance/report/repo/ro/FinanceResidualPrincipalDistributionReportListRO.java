package com.platform.finance.report.repo.ro;

import com.platform.finance.report.common.annotation.FileNameAnnotation;
import com.platform.finance.report.common.utils.excel.annotation.ExportBeanFieldAnnotation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:38
 * @description 剩余本金分布表
 */
@Data
@FileNameAnnotation(value = "剩余本金分布表")
public class FinanceResidualPrincipalDistributionReportListRO {

    /**
     * 资产渠道类型
     */
    @ExportBeanFieldAnnotation(sort = 1, title = "资产渠道类型")
    @ApiModelProperty("资产渠道类型")
    private String channelType;

    /**
     * 渠道名称
     */
    @ExportBeanFieldAnnotation(sort = 2, title = "渠道名称")
    @ApiModelProperty("渠道名称")
    private String channelName;

    /**
     * 产品类型
     */
    @ExportBeanFieldAnnotation(sort = 3, title = "产品类型")
    @ApiModelProperty("产品类型")
    private String productType;

    /**
     * 产品子类型
     */
    @ExportBeanFieldAnnotation(sort = 4, title = "产品子类型")
    @ApiModelProperty("产品子类型")
    private String productChildType;

    /**
     * 未到期本金
     */
    @ExportBeanFieldAnnotation(sort = 5, title = "未到期本金")
    @ApiModelProperty("未到期本金")
    private BigDecimal principalAmount;

    /**
     * 正常类
     */
    @ExportBeanFieldAnnotation(sort = 6, title = "正常类")
    @ApiModelProperty("正常类")
    private BigDecimal normalPrincipalAmount;

    /**
     * 关注类
     */
    @ExportBeanFieldAnnotation(sort = 7, title = "关注类")
    @ApiModelProperty("关注类")
    private BigDecimal followPrincipalAmount;

    /**
     * 次级类
     */
    @ExportBeanFieldAnnotation(sort = 8, title = "次级类")
    @ApiModelProperty("次级类")
    private BigDecimal secondaryPrincipalAmount;

    /**
     * 可疑
     */
    @ExportBeanFieldAnnotation(sort = 9, title = "可疑")
    @ApiModelProperty("可疑")
    private BigDecimal suspiciousPrincipalAmount;

    /**
     * 损失
     */
    @ExportBeanFieldAnnotation(sort = 10, title = "损失")
    @ApiModelProperty("损失")
    private BigDecimal lossPrincipalAmount;

    /**
     * 小计
     */
    @ExportBeanFieldAnnotation(sort = 11, title = "小计")
    @ApiModelProperty("小计")
    private BigDecimal subTotalAmount;

    /**
     * 合计
     */
    @ExportBeanFieldAnnotation(sort = 12, title = "合计")
    @ApiModelProperty("合计")
    private BigDecimal totalAmount;
}
