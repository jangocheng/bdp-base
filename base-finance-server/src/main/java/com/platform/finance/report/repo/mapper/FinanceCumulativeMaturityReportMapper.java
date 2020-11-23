package com.platform.finance.report.repo.mapper;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.CumulativeMaturityReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeMaturityReportListRO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 10:55
 * @description 到期表
 */
@Repository
public interface FinanceCumulativeMaturityReportMapper {

    /**
     * 查询到期表
     *
     * @param cumulativeMaturityReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceCumulativeMaturityReportListRO> selectCumulativeMaturityReport(@Param("list") CumulativeMaturityReportRO cumulativeMaturityReportRO, PageRequest pageRequest);


    /**
     * 导出到期表
     *
     * @param cumulativeMaturityReportRO
     * @return
     */
    List<FinanceCumulativeMaturityReportListRO> exportCumulativeMaturityReport(@Param("list") CumulativeMaturityReportRO cumulativeMaturityReportRO);
}
