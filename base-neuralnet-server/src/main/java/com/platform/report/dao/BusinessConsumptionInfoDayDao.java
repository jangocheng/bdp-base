package com.platform.report.dao;

import com.platform.report.bean.BusinessConsumptionInfoDay;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface BusinessConsumptionInfoDayDao extends BaseMapper<BusinessConsumptionInfoDay> {

    List<BusinessConsumptionInfoDay> selectConsumptionAmount(@Param("consumptionType") String consumptionType,
                                                             @Param("customerType") String customerType,
                                                             @Param("start") String startDate, @Param("end") String endDate);

    List<BusinessConsumptionInfoDay> selectConsumptionSum(@Param("consumptionType") String consumptionType,
                                                          @Param("customerType") String customerType,
                                                          @Param("start") String startDate, @Param("end") String endDate);

    List<BusinessConsumptionInfoDay> selectMaxConsumption(@Param("consumptionType") String consumptionType,
                                                          @Param("customerType") String customerType,
                                                          @Param("start") String startDate, @Param("end") String endDate);

    List<BusinessConsumptionInfoDay> selectConsumptionAmountSum(@Param("consumptionType") String consumptionType,
                                                                @Param("customerType") String customerType,
                                                                @Param("start") String startDate, @Param("end") String endDate);

    List<BusinessConsumptionInfoDay> selectSummaryByPayType(@Param("consumptionType") String consumptionType,
                                                            @Param("customerType") String customerType,
                                                            @Param("start") String startDate, @Param("end") String endDate);
}
