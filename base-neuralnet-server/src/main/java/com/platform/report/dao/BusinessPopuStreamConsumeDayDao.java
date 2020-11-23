package com.platform.report.dao;

import com.platform.report.bean.BusinessPopuStreamConsumeDay;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;


public interface BusinessPopuStreamConsumeDayDao extends BaseMapper<BusinessPopuStreamConsumeDay> {

    List<BusinessPopuStreamConsumeDay> selectGroupByChannel(@Param("channel") String channel, @Param("start") String start, @Param("end") String end);

    List<BusinessPopuStreamConsumeDay> selectGroupByRNameAndStoreName(@Param("channel") String channel, @Param("start") String start, @Param("end") String end);

    List<BusinessPopuStreamConsumeDay> selectGroupByBrandName(@Param("channel") String channel, @Param("start") String start, @Param("end") String end);
}
