package com.platform.finance.report.repo.mapper;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.CumulativeLoanReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeLoanReportListRO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 10:55
 * @description 放款表
 */
@Repository
public interface FinanceCumulativeLoanReportMapper {

    /**
     * 查询放款表
     *
     * @param cumulativeLoanReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceCumulativeLoanReportListRO> selectCumulativeLoanReport(@Param("list") CumulativeLoanReportRO cumulativeLoanReportRO, PageRequest pageRequest);


    /**
     * 导出放款表
     *
     * @param cumulativeLoanReportRO
     * @return
     */
    List<FinanceCumulativeLoanReportListRO> exportCumulativeLoanReport(@Param("list") CumulativeLoanReportRO cumulativeLoanReportRO);
}
