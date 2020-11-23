package com.platform.finance.report.service.impl;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.mapper.FinanceOverdueReportMapper;
import com.platform.finance.report.repo.ro.FinanceOverdueReportListRO;
import com.platform.finance.report.repo.ro.OverdueReportRO;
import com.platform.finance.report.service.FinanceOverdueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:54
 * @description 逾期表
 */
@Service
public class FinanceOverdueReportServiceImpl implements FinanceOverdueReportService {

    @Autowired
    private FinanceOverdueReportMapper financeOverdueReportMapper;

    /**
     * 查询逾期表
     *
     * @param overdueReportRO
     * @param pageRequest
     * @return
     */
    @Override
    public Page<FinanceOverdueReportListRO> selectOverdueReport(OverdueReportRO overdueReportRO, PageRequest pageRequest) {
        return financeOverdueReportMapper.selectOverdueReport(overdueReportRO, pageRequest);
    }

    /**
     * 导出逾期表
     *
     * @param overdueReportRO
     * @return
     */
    @Override
    public List<FinanceOverdueReportListRO> exportOverdueReport(OverdueReportRO overdueReportRO) {
        return financeOverdueReportMapper.exportOverdueReport(overdueReportRO);
    }
}
