package com.platform.report.dao;

import com.platform.report.bean.BusinessRegisterHour;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;


public interface BusinessRegisterHourDao extends BaseMapper<BusinessRegisterHour> {

    List<BusinessRegisterHour> selectRegisterRatio(@Param("start") String start, @Param("end") String end);

    List<BusinessRegisterHour> selectChannelRatio(@Param("start") String start, @Param("end") String end);

}
