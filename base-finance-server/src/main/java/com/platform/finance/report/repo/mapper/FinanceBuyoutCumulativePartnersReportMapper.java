package com.platform.finance.report.repo.mapper;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.BuyoutCumulativePartnersReportRO;
import com.platform.finance.report.repo.ro.FinanceBuyoutCumulativePartnersReportListRO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 10:55
 * @description 买断表
 */
@Repository
public interface FinanceBuyoutCumulativePartnersReportMapper {

    /**
     * 查询买断表
     *
     * @param buyoutCumulativePartnersReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceBuyoutCumulativePartnersReportListRO> selectBuyoutCumulativePartnersReport(@Param("list") BuyoutCumulativePartnersReportRO buyoutCumulativePartnersReportRO, PageRequest pageRequest);


    /**
     * 导出买断表
     *
     * @param buyoutCumulativePartnersReportRO
     * @return
     */
    List<FinanceBuyoutCumulativePartnersReportListRO> exportBuyoutCumulativePartnersReport(@Param("list") BuyoutCumulativePartnersReportRO buyoutCumulativePartnersReportRO);
}
