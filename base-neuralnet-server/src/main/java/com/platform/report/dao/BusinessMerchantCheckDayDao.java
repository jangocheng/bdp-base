package com.platform.report.dao;

import com.platform.report.bean.BusinessMerchantCheckDay;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface BusinessMerchantCheckDayDao extends BaseMapper<BusinessMerchantCheckDay> {

    List<BusinessMerchantCheckDay> selectTotalByBizDate(@Param("start") String start, @Param("end") String end);
}
