package com.platform.finance.report.service.impl;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.mapper.FinanceCumulativeMaturityReportMapper;
import com.platform.finance.report.repo.ro.CumulativeMaturityReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeMaturityReportListRO;
import com.platform.finance.report.service.FinanceCumulativeMaturityReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:48
 * @description 到期表
 */
@Service
public class FinanceCumulativeMaturityReportServiceImpl implements FinanceCumulativeMaturityReportService {

    @Autowired
    private FinanceCumulativeMaturityReportMapper financeCumulativeMaturityReportMapper;

    /**
     * 查询到期表
     *
     * @param cumulativeMaturityReportRO
     * @param pageRequest
     * @return
     */
    @Override
    public Page<FinanceCumulativeMaturityReportListRO> selectCumulativeMaturityReport(CumulativeMaturityReportRO cumulativeMaturityReportRO, PageRequest pageRequest) {
        return financeCumulativeMaturityReportMapper.selectCumulativeMaturityReport(cumulativeMaturityReportRO, pageRequest);
    }

    /**
     * 导出到期表
     *
     * @param cumulativeMaturityReportRO
     * @return
     */
    @Override
    public List<FinanceCumulativeMaturityReportListRO> exportCumulativeMaturityReport(CumulativeMaturityReportRO cumulativeMaturityReportRO) {
        return financeCumulativeMaturityReportMapper.exportCumulativeMaturityReport(cumulativeMaturityReportRO);
    }
}
