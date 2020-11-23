package com.platform.finance.report.repo.mapper;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.FinanceUndueReportListRO;
import com.platform.finance.report.repo.ro.UndueReportRO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 10:55
 * @description 未到期表
 */
@Repository
public interface FinanceUndueReportMapper {

    /**
     * 查询未到期表
     *
     * @param undueReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceUndueReportListRO> selectUndueReport(@Param("list") UndueReportRO undueReportRO, PageRequest pageRequest);


    /**
     * 导出未到期表
     *
     * @param undueReportRO
     * @return
     */
    List<FinanceUndueReportListRO> exportUndueReport(@Param("list") UndueReportRO undueReportRO);
}
