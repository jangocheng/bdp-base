package com.platform.finance.report.service.impl;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.mapper.FinanceBuyoutCumulativePartnersReportMapper;
import com.platform.finance.report.repo.ro.BuyoutCumulativePartnersReportRO;
import com.platform.finance.report.repo.ro.FinanceBuyoutCumulativePartnersReportListRO;
import com.platform.finance.report.service.FinanceBuyoutCumulativePartnersReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 16:13
 * @description 买断表
 */
@Service
public class FinanceBuyoutCumulativePartnersReportServiceImpl  implements FinanceBuyoutCumulativePartnersReportService {

    @Autowired
    private FinanceBuyoutCumulativePartnersReportMapper financeBuyoutCumulativePartnersReportMapper;

    /**
     * 查询买断表
     *
     * @param buyoutCumulativePartnersReportRO
     * @param pageRequest
     * @return
     */
    @Override
    public Page<FinanceBuyoutCumulativePartnersReportListRO> selectBuyoutCumulativePartnersReport(BuyoutCumulativePartnersReportRO buyoutCumulativePartnersReportRO, PageRequest pageRequest) {
        return financeBuyoutCumulativePartnersReportMapper.selectBuyoutCumulativePartnersReport(buyoutCumulativePartnersReportRO, pageRequest);
    }

    /**
     * 导出买断表
     *
     * @param buyoutCumulativePartnersReportRO
     * @return
     */
    @Override
    public List<FinanceBuyoutCumulativePartnersReportListRO> exportBuyoutCumulativePartnersReport(BuyoutCumulativePartnersReportRO buyoutCumulativePartnersReportRO) {
        return financeBuyoutCumulativePartnersReportMapper.exportBuyoutCumulativePartnersReport(buyoutCumulativePartnersReportRO);
    }
}
