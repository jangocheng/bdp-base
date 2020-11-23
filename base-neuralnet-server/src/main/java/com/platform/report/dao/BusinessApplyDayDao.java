package com.platform.report.dao;

import com.platform.report.bean.BusinessApplyDay;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface BusinessApplyDayDao extends BaseMapper<BusinessApplyDay> {

    List<BusinessApplyDay> selectChannels();

    List<BusinessApplyDay> selectChannelsByBizDate(@Param("start") String start, @Param("end") String end);
}
