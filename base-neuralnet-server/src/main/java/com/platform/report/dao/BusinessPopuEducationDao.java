package com.platform.report.dao;

import com.platform.report.bean.BusinessPopuEducation;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface BusinessPopuEducationDao extends BaseMapper<BusinessPopuEducation> {

    List<Map<String, Object>> selectTotal(@Param("start") String startDate,
                                          @Param("end") String endDate,
                                          @Param("channel") String channel);
}
