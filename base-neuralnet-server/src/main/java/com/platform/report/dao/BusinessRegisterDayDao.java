package com.platform.report.dao;

import com.platform.report.bean.BusinessRegisterDay;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;


public interface BusinessRegisterDayDao extends BaseMapper<BusinessRegisterDay> {

    List<BusinessRegisterDay> selectChannels();

    List<BusinessRegisterDay> selectChannelsByBizDate(@Param("start") String start, @Param("end") String end);
}
