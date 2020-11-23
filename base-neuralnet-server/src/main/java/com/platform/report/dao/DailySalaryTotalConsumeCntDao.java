package com.platform.report.dao;

import com.platform.report.bean.DailySalaryTotalConsumeCnt;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface DailySalaryTotalConsumeCntDao extends BaseMapper<DailySalaryTotalConsumeCnt> {
    List<DailySalaryTotalConsumeCnt> selectByBizDate(@Param("bizDate") String bizDate);
}
