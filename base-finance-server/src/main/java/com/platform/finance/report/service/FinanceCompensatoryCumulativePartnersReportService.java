package com.platform.finance.report.service;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.CompensatoryCumulativePartnersReportRO;
import com.platform.finance.report.repo.ro.FinanceCompensatoryCumulativePartnersReportListRO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:26
 * @description 代偿表
 */
public interface FinanceCompensatoryCumulativePartnersReportService {

    /**
     * 查询代偿表
     *
     * @param compensatoryCumulativePartnersReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceCompensatoryCumulativePartnersReportListRO> selectCompensatoryCumulativePartnersReport(CompensatoryCumulativePartnersReportRO compensatoryCumulativePartnersReportRO, PageRequest pageRequest);

    /**
     * 导出代偿表
     *
     * @param compensatoryCumulativePartnersReportRO
     * @return
     */
    List<FinanceCompensatoryCumulativePartnersReportListRO> exportCompensatoryCumulativePartnersReport(CompensatoryCumulativePartnersReportRO compensatoryCumulativePartnersReportRO);
}
