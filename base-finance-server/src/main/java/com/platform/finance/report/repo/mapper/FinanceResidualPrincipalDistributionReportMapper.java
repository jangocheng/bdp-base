package com.platform.finance.report.repo.mapper;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.FinanceResidualPrincipalDistributionReportListRO;
import com.platform.finance.report.repo.ro.ResidualPrincipalDistributionReportRO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 10:55
 * @description 剩余本金分布表
 */
@Repository
public interface FinanceResidualPrincipalDistributionReportMapper {

    /**
     * 查询剩余本金分布表
     *
     * @param residualPrincipalDistributionReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceResidualPrincipalDistributionReportListRO> selectResidualPrincipalDistributionReport(@Param("list") ResidualPrincipalDistributionReportRO residualPrincipalDistributionReportRO, PageRequest pageRequest);


    /**
     * 导出剩余本金分布表
     *
     * @param residualPrincipalDistributionReportRO
     * @return
     */
    List<FinanceResidualPrincipalDistributionReportListRO> exportResidualPrincipalDistributionReport(@Param("list") ResidualPrincipalDistributionReportRO residualPrincipalDistributionReportRO);
}
