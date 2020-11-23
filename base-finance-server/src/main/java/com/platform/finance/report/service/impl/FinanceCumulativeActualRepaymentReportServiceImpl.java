package com.platform.finance.report.service.impl;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.mapper.FinanceCumulativeActualRepaymentReportMapper;
import com.platform.finance.report.repo.ro.CumulativeActualRepaymentReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeActualRepaymentReportListRO;
import com.platform.finance.report.service.FinanceCumulativeActualRepaymentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:33
 * @description 实还表
 */
@Service
public class FinanceCumulativeActualRepaymentReportServiceImpl implements FinanceCumulativeActualRepaymentReportService {

    @Autowired
    FinanceCumulativeActualRepaymentReportMapper financeCumulativeActualRepaymentReportMapper;

    /**
     * 查询实还表
     *
     * @param cumulativeActualRepaymentReportRO
     * @param pageRequest
     * @return
     */
    @Override
    public Page<FinanceCumulativeActualRepaymentReportListRO> selectCumulativeActualRepaymentReport(CumulativeActualRepaymentReportRO cumulativeActualRepaymentReportRO, PageRequest pageRequest) {
        return financeCumulativeActualRepaymentReportMapper.selectCumulativeActualRepaymentReport(cumulativeActualRepaymentReportRO, pageRequest);
    }

    /**
     * 导出实还表
     *
     * @param cumulativeActualRepaymentReportRO
     * @return
     */
    @Override
    public List<FinanceCumulativeActualRepaymentReportListRO> exportCumulativeActualRepaymentReport(CumulativeActualRepaymentReportRO cumulativeActualRepaymentReportRO) {
        return financeCumulativeActualRepaymentReportMapper.exportCumulativeActualRepaymentReport(cumulativeActualRepaymentReportRO);
    }
}
