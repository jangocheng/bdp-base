package com.platform.report.dao;

import com.platform.report.bean.DailySalaryGroupCompletePct;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;


public interface DailySalaryGroupCompletePctDao extends BaseMapper<DailySalaryGroupCompletePct> {
    List<DailySalaryGroupCompletePct> selectByBizDate(@Param("bizDate") String bizDate);
}
