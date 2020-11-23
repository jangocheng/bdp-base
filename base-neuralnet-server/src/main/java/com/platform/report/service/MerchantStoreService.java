package com.platform.report.service;

import com.platform.report.common.entity.MessageTO;


public interface MerchantStoreService {
    MessageTO getStoreData(String start, String end);

    MessageTO getMerchantData(String start, String end);
}
