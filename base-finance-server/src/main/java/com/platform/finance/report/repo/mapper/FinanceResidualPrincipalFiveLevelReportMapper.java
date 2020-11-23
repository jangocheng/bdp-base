package com.platform.finance.report.repo.mapper;

import com.platform.finance.report.repo.ro.FinanceResidualPrincipalFiveLevelReportListRO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 10:55
 * @description 剩余本金五级分类表
 */
@Repository
public interface FinanceResidualPrincipalFiveLevelReportMapper {

    /**
     * 查询剩余本金五级分类表
     *
     * @param dataDateStart
     * @param dataDateEnd
     * @return
     */
    List<FinanceResidualPrincipalFiveLevelReportListRO> selectResidualPrincipalFiveLevelReport(@Param("dataDateStart") String dataDateStart, @Param("dataDateEnd") String dataDateEnd);

}
