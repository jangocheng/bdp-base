package com.platform.finance.report.service;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.CumulativeDeductionReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeDeductionReportListRO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:38
 * @description 减免表
 */
public interface FinanceCumulativeDeductionReportService {

    /**
     * 查询减免表
     *
     * @param cumulativeDeductionReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceCumulativeDeductionReportListRO> selectCumulativeDeductionReport(CumulativeDeductionReportRO cumulativeDeductionReportRO, PageRequest pageRequest);

    /**
     * 导出减免表
     *
     * @param cumulativeDeductionReportRO
     * @return
     */
    List<FinanceCumulativeDeductionReportListRO> exportCumulativeDeductionReport(CumulativeDeductionReportRO cumulativeDeductionReportRO);
}
