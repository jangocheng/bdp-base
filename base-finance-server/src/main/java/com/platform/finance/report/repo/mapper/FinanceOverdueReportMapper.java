package com.platform.finance.report.repo.mapper;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.FinanceOverdueReportListRO;
import com.platform.finance.report.repo.ro.OverdueReportRO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 10:55
 * @description 逾期表
 */
@Repository
public interface FinanceOverdueReportMapper {

    /**
     * 查询逾期表
     *
     * @param overdueReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceOverdueReportListRO> selectOverdueReport(@Param("list") OverdueReportRO overdueReportRO, PageRequest pageRequest);


    /**
     * 导出逾期表
     *
     * @param overdueReportRO
     * @return
     */
    List<FinanceOverdueReportListRO> exportOverdueReport(@Param("list") OverdueReportRO overdueReportRO);
}
