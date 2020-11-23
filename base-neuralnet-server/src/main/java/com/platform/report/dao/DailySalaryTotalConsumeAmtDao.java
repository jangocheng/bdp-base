package com.platform.report.dao;

import com.platform.report.bean.DailySalaryTotalConsumeAmt;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;


public interface DailySalaryTotalConsumeAmtDao extends BaseMapper<DailySalaryTotalConsumeAmt> {

    List<DailySalaryTotalConsumeAmt>  selectByBizDate(@Param("bizDate") String bizDate);
	
}
