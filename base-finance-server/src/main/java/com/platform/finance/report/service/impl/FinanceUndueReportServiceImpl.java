package com.platform.finance.report.service.impl;

import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.repo.mapper.FinanceUndueReportMapper;
import com.platform.finance.report.repo.ro.FinanceUndueReportListRO;
import com.platform.finance.report.repo.ro.UndueReportRO;
import com.platform.finance.report.service.FinanceUndueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/4 17:01
 * @description 未到期表
 */
@Service
public class FinanceUndueReportServiceImpl implements FinanceUndueReportService {

    @Autowired
    private FinanceUndueReportMapper financeUndueReportMapper;

    /**
     * 查询未到期表
     *
     * @param undueReportRO
     * @param pageRequest
     * @return
     */
    @Override
    public Page<FinanceUndueReportListRO> selectUndueReport(UndueReportRO undueReportRO, PageRequest pageRequest) {
        return financeUndueReportMapper.selectUndueReport(undueReportRO, pageRequest);
    }

    /**
     * 导出未到期表
     *
     * @param undueReportRO
     * @param pageRequest
     * @return
     */
    @Override
    public List<FinanceUndueReportListRO> exportUndueReport(UndueReportRO undueReportRO) {
        return financeUndueReportMapper.exportUndueReport(undueReportRO);
    }
}
