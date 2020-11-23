package com.platform.finance.report.service.impl;

import com.platform.finance.report.repo.mapper.FinanceResidualPrincipalFiveLevelReportMapper;
import com.platform.finance.report.repo.ro.FinanceResidualPrincipalFiveLevelReportListRO;
import com.platform.finance.report.service.FinanceResidualPrincipalFiveLevelReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 17:49
 * @description 剩余本金五级分类表
 */
@Service
public class FinanceResidualPrincipalFiveLevelReportServiceImpl implements FinanceResidualPrincipalFiveLevelReportService {

    @Autowired
    private FinanceResidualPrincipalFiveLevelReportMapper financeResidualPrincipalFiveLevelReportMapper;

    /**
     * 查询剩余本金五级分类表
     *
     * @param dataDateStart
     * @param dataDateEnd
     * @return
     */
    @Override
    public List<FinanceResidualPrincipalFiveLevelReportListRO> selectResidualPrincipalFiveLevelReport(String dataDateStart, String dataDateEnd) {
        return financeResidualPrincipalFiveLevelReportMapper.selectResidualPrincipalFiveLevelReport(dataDateStart, dataDateEnd);
    }
}
