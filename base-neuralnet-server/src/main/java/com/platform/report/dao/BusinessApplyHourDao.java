package com.platform.report.dao;

import com.platform.report.bean.BusinessApplyHour;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface BusinessApplyHourDao extends BaseMapper<BusinessApplyHour> {
    List<BusinessApplyHour> selectRegisterRatio(@Param("start") String start, @Param("end") String end);

    List<BusinessApplyHour> selectChannelRatio(@Param("start") String start, @Param("end") String end);
}
