package com.platform.report.dao;

import com.platform.report.bean.DailySalaryDailyConsumeCnt;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface DailySalaryDailyConsumeCntDao extends BaseMapper<DailySalaryDailyConsumeCnt> {
    List<DailySalaryDailyConsumeCnt> selectByBizDate(@Param("bizDate") String bizDate);
	
}
