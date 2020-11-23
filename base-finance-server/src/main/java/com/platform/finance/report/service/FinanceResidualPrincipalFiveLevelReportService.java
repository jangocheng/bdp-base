package com.platform.finance.report.service;

import com.platform.finance.report.repo.ro.FinanceResidualPrincipalFiveLevelReportListRO;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 17:37
 * @description 剩余本金五级分类表
 */
public interface FinanceResidualPrincipalFiveLevelReportService {

    /**
     * 查询剩余本金五级分类表
     *
     * @param dataDateStart
     * @param dataDateEnd
     * @return
     */
    List<FinanceResidualPrincipalFiveLevelReportListRO> selectResidualPrincipalFiveLevelReport(String dataDateStart, String dataDateEnd);
}
