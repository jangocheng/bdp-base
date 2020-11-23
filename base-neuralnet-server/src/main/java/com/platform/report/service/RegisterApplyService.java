package com.platform.report.service;

import com.platform.report.common.entity.MessageTO;


public interface RegisterApplyService {
    MessageTO getRegisterChannelByHour(String start, String end);

    MessageTO getRegisterDataByDay(String start, String end);

    MessageTO getApplyChannelByHour(String start, String end);

    MessageTO getApplyDataByDay(String start, String end);

    MessageTO getRegisterChannels(String start, String end);

    MessageTO getApplyChannels(String start, String end);

    //MessageTO getChannelCount(String type, Integer start, Integer end);
}
