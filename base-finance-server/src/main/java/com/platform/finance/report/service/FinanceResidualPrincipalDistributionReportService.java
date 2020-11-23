package com.platform.finance.report.service;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.FinanceResidualPrincipalDistributionReportListRO;
import com.platform.finance.report.repo.ro.ResidualPrincipalDistributionReportRO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:56
 * @description 剩余本金分布表
 */
public interface FinanceResidualPrincipalDistributionReportService {

    /**
     * 查询剩余本金分布表
     *
     * @param residualPrincipalDistributionReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceResidualPrincipalDistributionReportListRO> selectResidualPrincipalDistributionReport(ResidualPrincipalDistributionReportRO residualPrincipalDistributionReportRO, PageRequest pageRequest);

    /**
     * 导出剩余本金分布表
     *
     * @param residualPrincipalDistributionReportRO
     * @return
     */
    List<FinanceResidualPrincipalDistributionReportListRO> exportResidualPrincipalDistributionReport(ResidualPrincipalDistributionReportRO residualPrincipalDistributionReportRO);
}
