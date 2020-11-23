package com.platform.report.dao;

import com.platform.report.bean.BusinessConsumptionProductDay;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface BusinessConsumptionProductDayDao extends BaseMapper<BusinessConsumptionProductDay> {

    List<BusinessConsumptionProductDay> selectSummaryByProductType(@Param("consumptionType") String consumptionType,
                                                                   @Param("customerType") String customerType,
                                                                   @Param("start") String startDate, @Param("end") String endDate);
}
