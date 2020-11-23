package com.platform.finance.report.service;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.CumulativeActualRepaymentReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeActualRepaymentReportListRO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:30
 * @description 实还表
 */
public interface FinanceCumulativeActualRepaymentReportService {

    /**
     * 查询实还表
     *
     * @param cumulativeActualRepaymentReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceCumulativeActualRepaymentReportListRO> selectCumulativeActualRepaymentReport(CumulativeActualRepaymentReportRO cumulativeActualRepaymentReportRO, PageRequest pageRequest);

    /**
     * 导出实还表
     *
     * @param cumulativeActualRepaymentReportRO
     * @return
     */
    List<FinanceCumulativeActualRepaymentReportListRO> exportCumulativeActualRepaymentReport(CumulativeActualRepaymentReportRO cumulativeActualRepaymentReportRO);
}
