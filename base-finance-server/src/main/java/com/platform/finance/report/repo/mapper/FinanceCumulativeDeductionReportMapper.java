package com.platform.finance.report.repo.mapper;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.CumulativeDeductionReportRO;
import com.platform.finance.report.repo.ro.FinanceCumulativeDeductionReportListRO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 10:55
 * @description 减免表
 */
@Repository
public interface FinanceCumulativeDeductionReportMapper {

    /**
     * 查询减免表
     *
     * @param cumulativeDeductionReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceCumulativeDeductionReportListRO> selectCumulativeDeductionReport(@Param("list") CumulativeDeductionReportRO cumulativeDeductionReportRO, PageRequest pageRequest);


    /**
     * 导出减免表
     *
     * @param cumulativeDeductionReportRO
     * @return
     */
    List<FinanceCumulativeDeductionReportListRO> exportCumulativeDeductionReport(@Param("list") CumulativeDeductionReportRO cumulativeDeductionReportRO);
}
