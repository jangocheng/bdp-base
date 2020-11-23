package com.platform.finance.report.service.impl;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.mapper.FinanceResidualPrincipalDistributionReportMapper;
import com.platform.finance.report.repo.ro.FinanceResidualPrincipalDistributionReportListRO;
import com.platform.finance.report.repo.ro.ResidualPrincipalDistributionReportRO;
import com.platform.finance.report.service.FinanceResidualPrincipalDistributionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:57
 * @description 剩余本金分布表
 */
@Service
public class FinanceResidualPrincipalDistributionReportServiceImpl implements FinanceResidualPrincipalDistributionReportService {

    @Autowired
    private FinanceResidualPrincipalDistributionReportMapper financeResidualPrincipalDistributionReportMapper;

    /**
     * 查询剩余本金分布表
     *
     * @param residualPrincipalDistributionReportRO
     * @param pageRequest
     * @return
     */
    @Override
    public Page<FinanceResidualPrincipalDistributionReportListRO> selectResidualPrincipalDistributionReport(ResidualPrincipalDistributionReportRO residualPrincipalDistributionReportRO, PageRequest pageRequest) {
        return financeResidualPrincipalDistributionReportMapper.selectResidualPrincipalDistributionReport(residualPrincipalDistributionReportRO, pageRequest);
    }

    /**
     * 导出剩余本金分布表
     *
     * @param residualPrincipalDistributionReportRO
     * @return
     */
    @Override
    public List<FinanceResidualPrincipalDistributionReportListRO> exportResidualPrincipalDistributionReport(ResidualPrincipalDistributionReportRO residualPrincipalDistributionReportRO) {
        return financeResidualPrincipalDistributionReportMapper.exportResidualPrincipalDistributionReport(residualPrincipalDistributionReportRO);
    }
}