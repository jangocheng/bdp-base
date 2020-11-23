package com.platform.finance.report.service;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.CumulativeMaturityReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeMaturityReportListRO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:47
 * @description 到期表
 */
public interface FinanceCumulativeMaturityReportService {

    /**
     * 查询到期表
     *
     * @param cumulativeMaturityReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceCumulativeMaturityReportListRO> selectCumulativeMaturityReport(CumulativeMaturityReportRO cumulativeMaturityReportRO, PageRequest pageRequest);


    /**
     * 导出到期表
     *
     * @param cumulativeMaturityReportRO
     * @return
     */
    List<FinanceCumulativeMaturityReportListRO> exportCumulativeMaturityReport(CumulativeMaturityReportRO cumulativeMaturityReportRO);
}
