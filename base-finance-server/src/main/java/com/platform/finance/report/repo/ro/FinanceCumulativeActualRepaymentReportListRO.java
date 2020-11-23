package com.platform.finance.report.repo.ro;

import com.platform.finance.report.common.annotation.FileNameAnnotation;
import com.platform.finance.report.common.utils.excel.annotation.ExportBeanFieldAnnotation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wlhbdp
 * @date 2020/9/2 14:08
 * @description 实还表
 */
@Data
@FileNameAnnotation(value = "实还表")
public class FinanceCumulativeActualRepaymentReportListRO {

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
     * 还款类型
     */
    @ExportBeanFieldAnnotation(sort = 7, title = "还款类型")
    @ApiModelProperty("还款类型")
    private String payType;

    /**
     * 应还年月
     */
    @ExportBeanFieldAnnotation(sort = 8, title = "应还年月")
    @ApiModelProperty("应还年月")
    private String payDate;

    /**
     * 还款年月
     */
    @ExportBeanFieldAnnotation(sort = 9, title = "还款年月")
    @ApiModelProperty("还款年月")
    private String repayDate;

    /**
     * 代偿年月
     */
    @ExportBeanFieldAnnotation(sort = 10, title = "代偿年月")
    @ApiModelProperty("代偿年月")
    private String compensatoryDate;

    /**
     * 买断年月
     */
    @ExportBeanFieldAnnotation(sort = 11, title = "买断年月")
    @ApiModelProperty("买断年月")
    private String buyOutDate;

    /**
     * 还款状态
     */
    @ExportBeanFieldAnnotation(sort = 12, title = "还款状态")
    @ApiModelProperty("还款状态")
    private String payStatus;

    /**
     * 十四级分类
     */
    @ExportBeanFieldAnnotation(sort = 13, title = "十四级分类")
    @ApiModelProperty("十四级分类")
    private String overdueGrade;

    /**
     * 实还本金
     */
    @ExportBeanFieldAnnotation(sort = 14, title = "实还本金")
    @ApiModelProperty("实还本金")
    private BigDecimal actualPrincipalAmount;

    /**
     * 实还利息
     */
    @ExportBeanFieldAnnotation(sort = 15, title = "实还利息")
    @ApiModelProperty("实还利息")
    private BigDecimal actualInterestAmount;

    /**
     * 实还罚息
     */
    @ExportBeanFieldAnnotation(sort = 16, title = "实还罚息")
    @ApiModelProperty("实还罚息")
    private BigDecimal actualPenaltyAmount;

    /**
     * 实还服务费
     */
    @ExportBeanFieldAnnotation(sort = 17, title = "实还服务费")
    @ApiModelProperty("实还服务费")
    private BigDecimal actualServiceAmount;

    /**
     * 提前结清手续费
     */
    @ExportBeanFieldAnnotation(sort = 19, title = "提前结清手续费")
    @ApiModelProperty("提前结清手续费")
    private BigDecimal actualPrepareAmount;

    /**
     * 合计
     */
    @ExportBeanFieldAnnotation(sort = 20, title = "合计")
    @ApiModelProperty("合计")
    private BigDecimal totalAmount;
}
