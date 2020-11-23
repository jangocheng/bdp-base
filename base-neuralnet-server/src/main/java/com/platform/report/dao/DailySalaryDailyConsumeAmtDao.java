package com.platform.report.dao;

import com.platform.report.bean.DailySalaryDailyConsumeAmt;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface DailySalaryDailyConsumeAmtDao extends BaseMapper<DailySalaryDailyConsumeAmt> {
    List<DailySalaryDailyConsumeAmt> selectByBizDate(@Param("bizDate") String bizDate);
}
