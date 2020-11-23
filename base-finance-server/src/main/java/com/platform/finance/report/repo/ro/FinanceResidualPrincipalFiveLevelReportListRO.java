package com.platform.finance.report.repo.ro;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wlhbdp
 * @date 2020/9/4 17:11
 * @description 剩余本金五级分类表
 */
@Data
public class FinanceResidualPrincipalFiveLevelReportListRO {

    /**
     * 日期
     */
    private String dataDate;

    /**
     * C
     */
    private BigDecimal zeroLevelAmount;

    /**
     * M1
     */
    private BigDecimal oneLevelAmount;

    /**
     * M2
     */
    private BigDecimal twoLevelAmount;

    /**
     * M3
     */
    private BigDecimal threeLevelAmount;

    /**
     * M4
     */
    private BigDecimal fourLevelAmount;

    /**
     * M5
     */
    private BigDecimal fiveLevelAmount;

    /**
     * M6
     */
    private BigDecimal sixLevelAmount;

    /**
     * M7
     */
    private BigDecimal sevenLevelAmount;

    /**
     * M8
     */
    private BigDecimal eightLevelAmount;

    /**
     * M9
     */
    private BigDecimal nineLevelAmount;

    /**
     * M10
     */
    private BigDecimal tenLevelAmount;

    /**
     * M11
     */
    private BigDecimal elevenLevelAmount;

    /**
     * M12
     */
    private BigDecimal twelveLevelAmount;

    /**
     * M13
     */
    private BigDecimal thirteenLevelAmount;

    /**
     * 合计
     */
    private BigDecimal totalAmount;
}
