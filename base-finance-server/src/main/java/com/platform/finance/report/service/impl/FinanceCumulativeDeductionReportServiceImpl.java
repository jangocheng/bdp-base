package com.platform.finance.report.service.impl;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.mapper.FinanceCumulativeDeductionReportMapper;
import com.platform.finance.report.repo.ro.CumulativeDeductionReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeDeductionReportListRO;
import com.platform.finance.report.service.FinanceCumulativeDeductionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:39
 * @description 减免表
 */
@Service
public class FinanceCumulativeDeductionReportServiceImpl implements FinanceCumulativeDeductionReportService {

    @Autowired
    private FinanceCumulativeDeductionReportMapper financeCumulativeDeductionReportMapper;

    /**
     * 查询减免表
     *
     * @param cumulativeDeductionReportRO
     * @param pageRequest
     * @return
     */
    @Override
    public Page<FinanceCumulativeDeductionReportListRO> selectCumulativeDeductionReport(CumulativeDeductionReportRO cumulativeDeductionReportRO, PageRequest pageRequest) {
        return financeCumulativeDeductionReportMapper.selectCumulativeDeductionReport(cumulativeDeductionReportRO, pageRequest);
    }

    /**
     * 导出减免表
     *
     * @param cumulativeDeductionReportRO
     * @return
     */
    @Override
    public List<FinanceCumulativeDeductionReportListRO> exportCumulativeDeductionReport(CumulativeDeductionReportRO cumulativeDeductionReportRO) {
        return financeCumulativeDeductionReportMapper.exportCumulativeDeductionReport(cumulativeDeductionReportRO);
    }
}
