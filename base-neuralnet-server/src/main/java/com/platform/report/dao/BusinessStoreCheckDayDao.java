package com.platform.report.dao;

import com.platform.report.bean.BusinessStoreCheckDay;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface BusinessStoreCheckDayDao extends BaseMapper<BusinessStoreCheckDay> {

    List<BusinessStoreCheckDay> selectTotalByBizDate(@Param("start") String start, @Param("end") String end);
}
