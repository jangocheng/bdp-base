package com.platform.report.service;

import com.platform.report.common.entity.MessageTO;
import com.platform.report.controller.mo.BusinessPWReqMO;


public interface PaymentWithdrawService {
    MessageTO getConsumptionAmount(BusinessPWReqMO pwReqVO);

    MessageTO getConsumptionSum(BusinessPWReqMO pwReqVO);

    MessageTO getMaximumConsumption(BusinessPWReqMO pwReqVO);

    MessageTO getAvgConsumption(BusinessPWReqMO pwReqVO);

    MessageTO getPWRatePayType(BusinessPWReqMO pwReqVO);

    MessageTO getRateProduct(BusinessPWReqMO pwReqVO);

    MessageTO getConsumptionType();
}
