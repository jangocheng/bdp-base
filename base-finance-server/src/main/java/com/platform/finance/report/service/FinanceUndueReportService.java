package com.platform.finance.report.service;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.ro.FinanceUndueReportListRO;
import com.platform.finance.report.repo.ro.UndueReportRO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 17:00
 * @description 未到期表
 */
public interface FinanceUndueReportService {

    /**
     * 查询未到期表
     *
     * @param undueReportRO
     * @param pageRequest
     * @return
     */
    Page<FinanceUndueReportListRO> selectUndueReport(UndueReportRO undueReportRO, PageRequest pageRequest);

    /**
     * 导出未到期表
     *
     * @param undueReportRO
     * @return
     */
    List<FinanceUndueReportListRO> exportUndueReport(UndueReportRO undueReportRO);

}
