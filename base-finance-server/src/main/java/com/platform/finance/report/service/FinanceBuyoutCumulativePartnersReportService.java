package com.platform.finance.report.service;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.BuyoutCumulativePartnersReportRO;
import com.platform.finance.report.repo.ro.FinanceBuyoutCumulativePartnersReportListRO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:12
 * @description 买断表
 */
public interface FinanceBuyoutCumulativePartnersReportService {

    /**
     * 查询买断表
     *
     * @param buyoutCumulativePartnersReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceBuyoutCumulativePartnersReportListRO> selectBuyoutCumulativePartnersReport(BuyoutCumulativePartnersReportRO buyoutCumulativePartnersReportRO, PageRequest pageRequest);

    /**
     * 导出买断表
     *
     * @param buyoutCumulativePartnersReportRO
     * @return
     */
    List<FinanceBuyoutCumulativePartnersReportListRO> exportBuyoutCumulativePartnersReport(BuyoutCumulativePartnersReportRO buyoutCumulativePartnersReportRO);
}
