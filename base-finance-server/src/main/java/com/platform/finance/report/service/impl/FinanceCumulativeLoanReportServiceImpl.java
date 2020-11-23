package com.platform.finance.report.service.impl;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.mapper.FinanceCumulativeLoanReportMapper;
import com.platform.finance.report.repo.ro.CumulativeLoanReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeLoanReportListRO;
import com.platform.finance.report.service.FinanceCumulativeLoanReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:44
 * @description 放款表
 */
@Service
public class FinanceCumulativeLoanReportServiceImpl implements FinanceCumulativeLoanReportService {

    @Autowired
    private FinanceCumulativeLoanReportMapper financeCumulativeLoanReportMapper;

    /**
     * 查询放款表
     *
     * @param cumulativeLoanReportRO
     * @param pageRequest
     * @return
     */
    @Override
    public Page<FinanceCumulativeLoanReportListRO> selectCumulativeLoanReport(CumulativeLoanReportRO cumulativeLoanReportRO, PageRequest pageRequest) {
        return financeCumulativeLoanReportMapper.selectCumulativeLoanReport(cumulativeLoanReportRO, pageRequest);
    }

    /**
     * 导出放款表
     *
     * @param cumulativeLoanReportRO
     * @param pageRequest
     * @return
     */
    @Override
    public List<FinanceCumulativeLoanReportListRO> exportCumulativeLoanReport(CumulativeLoanReportRO cumulativeLoanReportRO) {
        return financeCumulativeLoanReportMapper.exportCumulativeLoanReport(cumulativeLoanReportRO);
    }
}
