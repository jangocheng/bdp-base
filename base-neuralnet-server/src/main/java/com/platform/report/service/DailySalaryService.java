package com.platform.report.service;

import com.platform.spring.bean.MessageTO;


public interface DailySalaryService {
    MessageTO getRTConsumeDataList();

    MessageTO getSyncGroupsDataList();

    MessageTO getGroupsConsumeAmtList();

    MessageTO getGroupsConsumeCtnList();

    MessageTO getGroupsSummaryData();

    MessageTO getGroupsPntCompleteData();
}
