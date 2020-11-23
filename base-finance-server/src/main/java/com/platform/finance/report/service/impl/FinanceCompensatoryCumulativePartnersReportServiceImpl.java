package com.platform.finance.report.service.impl;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.mapper.FinanceCompensatoryCumulativePartnersReportMapper;
import com.platform.finance.report.repo.ro.CompensatoryCumulativePartnersReportRO;
import com.platform.finance.report.repo.ro.FinanceCompensatoryCumulativePartnersReportListRO;
import com.platform.finance.report.service.FinanceCompensatoryCumulativePartnersReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:27
 * @description 代偿表
 */
@Service
public class FinanceCompensatoryCumulativePartnersReportServiceImpl implements FinanceCompensatoryCumulativePartnersReportService {

    @Autowired
    private FinanceCompensatoryCumulativePartnersReportMapper financeCompensatoryCumulativePartnersReportMapper;

    /**
     * 查询代偿表
     *
     * @param compensatoryCumulativePartnersReportRO
     * @param pageRequest
     * @return
     */
    @Override
    public Page<FinanceCompensatoryCumulativePartnersReportListRO> selectCompensatoryCumulativePartnersReport(CompensatoryCumulativePartnersReportRO compensatoryCumulativePartnersReportRO, PageRequest pageRequest) {
        return financeCompensatoryCumulativePartnersReportMapper.selectCompensatoryCumulativePartnersReport(compensatoryCumulativePartnersReportRO, pageRequest);
    }

    /**
     * 导出代偿表
     *
     * @param compensatoryCumulativePartnersReportRO
     * @return
     */
    @Override
    public List<FinanceCompensatoryCumulativePartnersReportListRO> exportCompensatoryCumulativePartnersReport(CompensatoryCumulativePartnersReportRO compensatoryCumulativePartnersReportRO) {
        return financeCompensatoryCumulativePartnersReportMapper.exportCompensatoryCumulativePartnersReport(compensatoryCumulativePartnersReportRO);
    }
}
