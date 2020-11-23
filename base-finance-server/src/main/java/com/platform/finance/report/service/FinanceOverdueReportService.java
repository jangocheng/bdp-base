package com.platform.finance.report.service;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.FinanceOverdueReportListRO;
import com.platform.finance.report.repo.ro.OverdueReportRO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:52
 * @description 逾期表
 */
public interface FinanceOverdueReportService {

    /**
     * 查询逾期表
     *
     * @param overdueReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceOverdueReportListRO> selectOverdueReport(OverdueReportRO overdueReportRO, PageRequest pageRequest);

    /**
     * 导出逾期表
     *
     * @param overdueReportRO
     * @return
     */
    List<FinanceOverdueReportListRO> exportOverdueReport(OverdueReportRO overdueReportRO);
}
