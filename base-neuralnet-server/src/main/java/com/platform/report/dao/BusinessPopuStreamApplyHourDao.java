package com.platform.report.dao;

import com.platform.report.bean.BusinessPopuStreamApplyHour;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;


public interface BusinessPopuStreamApplyHourDao extends BaseMapper<BusinessPopuStreamApplyHour> {
    List<BusinessPopuStreamApplyHour> selectRegisterRatio(@Param("start") String start, @Param("end") String end);

    List<BusinessPopuStreamApplyHour> selectChannelRatio(@Param("start") String start, @Param("end") String end);

}
