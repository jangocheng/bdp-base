package com.platform.report.service;

import com.platform.report.common.entity.MessageTO;

import java.util.List;
import java.util.Map;

public interface PopularizeService {


    MessageTO getChannelReport(String start, String end);

    MessageTO getStreamChannelByHour(String start, String end);

    MessageTO getStreamApplyDataByDay(String start, String end);

    MessageTO getStreamConsumptionData(String channel, String start, String end);

    MessageTO getStreamStoreData(String channel, String start, String end);

    MessageTO getStreamBrandData(String channel, String start, String end);

    List<Map<String, Object>> getAgeSummary(String channel, String start, String end);

    List<Map<String, Object>> getGenderSummary(String channel, String start, String end);

    List<Map<String, Object>> getProvinceSummary(String channel, String start, String end);

    List<Map<String, Object>> getCustomerLevelSummary(String channel, String start, String end);

    List<Map<String, Object>> getLoanPeriodSummary(String channel, String start, String end);

    List<Map<String, Object>> getMobileModelSummary(String channel, String start, String end);

    List<Map<String, Object>> getEduSummary(String channel, String start, String end);
}
