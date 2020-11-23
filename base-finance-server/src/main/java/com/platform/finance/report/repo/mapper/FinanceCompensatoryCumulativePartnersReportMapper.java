package com.platform.finance.report.repo.mapper;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.CompensatoryCumulativePartnersReportRO;
import com.platform.finance.report.repo.ro.FinanceCompensatoryCumulativePartnersReportListRO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 10:55
 * @description 代偿表
 */
@Repository
public interface FinanceCompensatoryCumulativePartnersReportMapper {

    /**
     * 查询代偿表
     *
     * @param compensatoryCumulativePartnersReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceCompensatoryCumulativePartnersReportListRO> selectCompensatoryCumulativePartnersReport(@Param("list") CompensatoryCumulativePartnersReportRO compensatoryCumulativePartnersReportRO, PageRequest pageRequest);


    /**
     * 导出代偿表
     *
     * @param compensatoryCumulativePartnersReportRO
     * @return
     */
    List<FinanceCompensatoryCumulativePartnersReportListRO> exportCompensatoryCumulativePartnersReport(@Param("list") CompensatoryCumulativePartnersReportRO compensatoryCumulativePartnersReportRO);
}
