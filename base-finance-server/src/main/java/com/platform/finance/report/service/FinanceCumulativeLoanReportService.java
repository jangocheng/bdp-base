package com.platform.finance.report.service;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.CumulativeLoanReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeLoanReportListRO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:43
 * @description 放款表
 */
public interface FinanceCumulativeLoanReportService {

    /**
     * 查询放款表
     *
     * @param cumulativeLoanReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceCumulativeLoanReportListRO> selectCumulativeLoanReport(CumulativeLoanReportRO cumulativeLoanReportRO, PageRequest pageRequest);

    /**
     * 导出放款表
     *
     * @param cumulativeLoanReportRO
     * @param pageRequest
     * @return
     */
    List<FinanceCumulativeLoanReportListRO> exportCumulativeLoanReport(CumulativeLoanReportRO cumulativeLoanReportRO);
}
