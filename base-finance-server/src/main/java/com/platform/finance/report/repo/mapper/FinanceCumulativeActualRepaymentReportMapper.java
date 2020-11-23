package com.platform.finance.report.repo.mapper;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.CumulativeActualRepaymentReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeActualRepaymentReportListRO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 10:55
 * @description 实还表
 */
@Repository
public interface FinanceCumulativeActualRepaymentReportMapper {

    /**
     * 查询实还表
     *
     * @param cumulativeActualRepaymentReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceCumulativeActualRepaymentReportListRO> selectCumulativeActualRepaymentReport(@Param("list") CumulativeActualRepaymentReportRO cumulativeActualRepaymentReportRO, PageRequest pageRequest);


    /**
     * 导出实还表
     *
     * @param cumulativeActualRepaymentReportRO
     * @return
     */
    List<FinanceCumulativeActualRepaymentReportListRO> exportCumulativeActualRepaymentReport(@Param("list") CumulativeActualRepaymentReportRO cumulativeActualRepaymentReportRO);
}
