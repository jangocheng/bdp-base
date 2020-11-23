package com.platform.report.service.impl;

import com.platform.report.bean.*;
import com.platform.report.common.entity.MessageTO;
import com.platform.report.common.utils.ResponseUtils;
import com.platform.report.controller.mo.BusinessPWReqMO;
import com.platform.report.controller.vo.*;
import com.platform.report.dao.*;
import com.platform.report.service.PaymentWithdrawService;
import com.platform.spring.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wlhbdp
 */
@Service
@Log4j2
public class PaymentWithdrawServiceImpl implements PaymentWithdrawService {

    @Resource
    private BusinessConsumptionInfoDayDao businessConsumptionInfoDayDao;
    @Resource
    private BusinessConsumptionProductDayDao businessConsumptionProductDayDao;


    @Override
    public MessageTO getConsumptionAmount(BusinessPWReqMO pwReqVO) {
        //参数校验
        String consumptionType = null;
        String customerType = null;
        if (StringUtils.isNotBlank(pwReqVO.getConsumptionType())) {
            consumptionType = pwReqVO.getConsumptionType();
        }
        if (StringUtils.isNotBlank(pwReqVO.getCustomerType())) {
            customerType = pwReqVO.getCustomerType();
        }

        //查询数据
        List<BusinessConsumptionInfoDay> data;
        try {
            data = businessConsumptionInfoDayDao.selectConsumptionAmount(consumptionType, customerType, pwReqVO.getStartDate(), pwReqVO.getEndDate());

        } catch (ServiceException se) {
            log.error("查询交易笔数失败，查询参数为:{},异常信息：{}", pwReqVO, se);
            return ResponseUtils.failure("查询交易笔数失败，请及时联系管理员！！");
        }

        //處理結果
        List<BusinessConsumptionAmountVO> rows = data.stream().map(e -> {
            BusinessConsumptionAmountVO amountVO = new BusinessConsumptionAmountVO();
            amountVO.setAmount(e.getConsumptionAmount());
            amountVO.setDate(e.getBizDate());
            amountVO.setConsumptionType(e.getConsumptionType());
            return amountVO;
        }).collect(Collectors.toList());

        return ResponseUtils.success(rows);
    }

    @Override
    public MessageTO getConsumptionSum(BusinessPWReqMO pwReqVO) {
        //参数校验
        String consumptionType = null;
        String customerType = null;
        if (StringUtils.isNotBlank(pwReqVO.getConsumptionType())) {
            consumptionType = pwReqVO.getConsumptionType();
        }
        if (StringUtils.isNotBlank(pwReqVO.getCustomerType())) {
            customerType = pwReqVO.getCustomerType();
        }

        //查询数据库
        List<BusinessConsumptionInfoDay> data;
        try {
            data = businessConsumptionInfoDayDao.selectConsumptionSum(consumptionType, customerType, pwReqVO.getStartDate(), pwReqVO.getEndDate());

        } catch (ServiceException se) {
            log.error("查询交易金额失败，查询参数为:{},异常信息：{}", pwReqVO, se);
            return ResponseUtils.failure("查询交易金额失败，请及时联系管理员！！");
        }

        //处理结果
        List<BusinessConsumptionSumVO> rows = data.stream().map(e -> {
            BusinessConsumptionSumVO amountVO = new BusinessConsumptionSumVO();
            amountVO.setSum(e.getConsumptionSum());
            amountVO.setDate(e.getBizDate());
            amountVO.setConsumptionType(e.getConsumptionType());
            return amountVO;
        }).collect(Collectors.toList());

        return ResponseUtils.success(rows);
    }

    @Override
    public MessageTO getMaximumConsumption(BusinessPWReqMO pwReqVO) {
        //参数校验
        String consumptionType = null;
        String customerType = null;
        if (StringUtils.isNotBlank(pwReqVO.getConsumptionType())) {
            consumptionType = pwReqVO.getConsumptionType();
        }
        if (StringUtils.isNotBlank(pwReqVO.getCustomerType())) {
            customerType = pwReqVO.getCustomerType();
        }

        //查询数据库
        List<BusinessConsumptionInfoDay> data;
        try {
            data = businessConsumptionInfoDayDao.selectMaxConsumption(consumptionType, customerType, pwReqVO.getStartDate(), pwReqVO.getEndDate());
        } catch (ServiceException se) {
            log.error("查询最大单笔金额失败，查询参数为:{},异常信息：{}", pwReqVO, se);
            return ResponseUtils.failure("查询最大单笔金额失败，请及时联系管理员！！");
        }

        //处理结果
        List<BusinessConsumptionSumVO> rows = data.stream().map(e -> {
            BusinessConsumptionSumVO amountVO = new BusinessConsumptionSumVO();
            amountVO.setSum(e.getMaxConsumptionSum());
            amountVO.setDate(e.getBizDate());
            amountVO.setConsumptionType(e.getConsumptionType());
            return amountVO;
        }).collect(Collectors.toList());

        return ResponseUtils.success(rows);

    }

    @Override
    public MessageTO getAvgConsumption(BusinessPWReqMO pwReqVO) {
        //参数校验
        String consumptionType = null;
        String customerType = null;
        if (StringUtils.isNotBlank(pwReqVO.getConsumptionType())) {
            consumptionType = pwReqVO.getConsumptionType();
        }
        if (StringUtils.isNotBlank(pwReqVO.getCustomerType())) {
            customerType = pwReqVO.getCustomerType();
        }

        //查询数据
        List<BusinessConsumptionInfoDay> data;
        try {
            data = businessConsumptionInfoDayDao.selectConsumptionAmountSum(consumptionType, customerType, pwReqVO.getStartDate(), pwReqVO.getEndDate());
        } catch (ServiceException se) {
            log.error("查询笔均交易金额失败，查询参数为:{},异常信息：{}", pwReqVO, se);
            return ResponseUtils.failure("查询笔均交易金额失败，请及时联系管理员！！");
        }

        //处理结果
        List<BusinessConsumptionSumVO> rows = data.stream().map(e -> {
            BusinessConsumptionSumVO amountVO = new BusinessConsumptionSumVO();
            amountVO.setSum(e.getConsumptionSum() / e.getConsumptionAmount());
            amountVO.setDate(e.getBizDate());
            amountVO.setConsumptionType(e.getConsumptionType());
            return amountVO;
        }).collect(Collectors.toList());

        return ResponseUtils.success(rows);
    }

    @Override
    public MessageTO getPWRatePayType(BusinessPWReqMO pwReqVO) {
        //参数校验
        String consumptionType = null;
        String customerType = null;
        if (StringUtils.isNotBlank(pwReqVO.getConsumptionType())) {
            consumptionType = pwReqVO.getConsumptionType();
        }
        if (StringUtils.isNotBlank(pwReqVO.getCustomerType())) {
            customerType = pwReqVO.getCustomerType();
        }

        //查询数据库
        List<BusinessConsumptionInfoDay> data;
        try {
            data = businessConsumptionInfoDayDao.selectSummaryByPayType(consumptionType, customerType, pwReqVO.getStartDate(), pwReqVO.getEndDate());
        } catch (ServiceException se) {
            log.error("查询支付类型占比失败，查询参数为:{},异常信息：{}", pwReqVO, se);
            return ResponseUtils.failure("查询支付类型占比失败，请及时联系管理员！！");
        }

        List<BusinessConsumptionRateVO> rows = data.stream().map(e -> {
            BusinessConsumptionRateVO consumptionVO = new BusinessConsumptionRateVO();
            consumptionVO.setConsumptionType(e.getConsumptionType());
            consumptionVO.setConsumptionSum(e.getConsumptionSum());
            return consumptionVO;
        }).collect(Collectors.toList());

        return ResponseUtils.success(rows);

    }

    @Override
    public MessageTO getRateProduct(BusinessPWReqMO pwReqVO) {
        //参数校验
        String consumptionType = null;
        String customerType = null;
        if (StringUtils.isNotBlank(pwReqVO.getConsumptionType())) {
            consumptionType = pwReqVO.getConsumptionType();
        }
        if (StringUtils.isNotBlank(pwReqVO.getCustomerType())) {
            customerType = pwReqVO.getCustomerType();
        }

        //查询数据库
        List<BusinessConsumptionProductDay> data;
        try {
            data = businessConsumptionProductDayDao.selectSummaryByProductType(consumptionType, customerType, pwReqVO.getStartDate(), pwReqVO.getEndDate());

        } catch (ServiceException se) {
            log.error("查询商品种类分布失败，查询参数为:{},异常信息：{}", pwReqVO, se);
            return ResponseUtils.failure("查询商品种类分布失败，请及时联系管理员！！");
        }

        //处理结果
        List<BusinessProductRateVO> rows = data.stream().map(e -> {
            BusinessProductRateVO productRateVO = new BusinessProductRateVO();
            productRateVO.setConsumptionSum(e.getConsumptionSum());
            productRateVO.setProductType(e.getProductType());
            return productRateVO;
        }).collect(Collectors.toList());

        return ResponseUtils.success(rows);
    }

    @Override
    public MessageTO getConsumptionType() {
        try {
            //查询数据库
            List<BusinessConsumptionInfoDay> data = businessConsumptionInfoDayDao
                    .createQuery()
                    .groupBy("consumption_type")
                    .select("consumption_type");

            List<String> rows = data.stream().map(BusinessConsumptionInfoDay::getConsumptionType).collect(Collectors.toList());

            return ResponseUtils.success(rows);
        } catch (ServiceException se) {
            log.error("查询支付类型失败，异常信息：{}", se);
            return ResponseUtils.failure("查询支付类型失败，请及时联系管理员！！");
        }
    }
}
