package com.platform.report.service.impl;

import com.platform.report.bean.*;
import com.platform.report.common.constant.BiCommon;
import com.platform.report.common.entity.MessageTO;
import com.platform.report.common.utils.ResponseUtils;
import com.platform.report.controller.vo.BusinessMerchantCheckVO;
import com.platform.report.controller.vo.BusinessStoreCheckVO;
import com.platform.report.dao.*;
import com.platform.report.service.MerchantStoreService;
import com.platform.spring.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wlhbdp
 */
@Service
@Log4j2
public class MerchantStoreServiceImpl implements MerchantStoreService {

    @Resource
    private BusinessStoreCheckDayDao businessStoreCheckDayDao;
    @Resource
    private BusinessMerchantCheckDayDao businessMerchantCheckDayDao;

    @Override
    public MessageTO getMerchantData(String start, String end) {
        try {
            List<BusinessMerchantCheckDay> data = businessMerchantCheckDayDao.createQuery()
                    .andBetween(BiCommon.BIZ_DATE_COL, start, end)
                    .desc(BiCommon.BIZ_DATE_COL)
                    .select();
            List<BusinessMerchantCheckDay> total = businessMerchantCheckDayDao.selectTotalByBizDate(start, end);
            Map<String, BusinessMerchantCheckDay> totalMap = total.stream().collect(Collectors.toMap(BusinessMerchantCheckDay::getBizDate, e -> e));

            List<BusinessMerchantCheckVO> rows = data.stream().map(e -> {
                BusinessMerchantCheckVO dataVO = new BusinessMerchantCheckVO();
                dataVO.setDate(e.getBizDate());
                dataVO.setApplyAmount(e.getApplyAmount());
                dataVO.setAccessAmount(e.getAccessAmount());
                dataVO.setAccessApplyAmount(e.getAccessApplyAmount());
                dataVO.setAccessFirstTrialAmount(e.getAccessFirstTrialAmount());
                dataVO.setActivationAmount(e.getActivationAmount());
                dataVO.setApplyApprovedAmount(e.getApplyApprovedAmount());
                dataVO.setApprovalRefuseAmount(e.getApprovalRefuseAmount());
                dataVO.setCloseAmount(e.getCloseAmount());
                dataVO.setFirstTrialAmount(e.getFirstTrialAmount());
                dataVO.setNewAmount(e.getNewAmount());
                dataVO.setRefuseAmount(e.getRefuseAmount());
                dataVO.setReturnSupplementaryAmount(e.getReturnSupplementaryAmount());

                BusinessMerchantCheckDay totalData = totalMap.get(e.getBizDate());
                dataVO.setApplyTotalAmount(totalData.getApplyAmount());
                dataVO.setAccessTotalAmount(totalData.getAccessAmount());
                dataVO.setAccessApplyTotalAmount(totalData.getAccessApplyAmount());
                dataVO.setAccessFirstTrialTotalAmount(totalData.getAccessFirstTrialAmount());
                dataVO.setActivationTotalAmount(totalData.getActivationAmount());
                dataVO.setApplyApprovedTotalAmount(totalData.getApplyApprovedAmount());
                dataVO.setApprovalRefuseTotalAmount(totalData.getApprovalRefuseAmount());
                dataVO.setCloseTotalAmount(totalData.getCloseAmount());
                dataVO.setFirstTrialTotalAmount(totalData.getFirstTrialAmount());
                dataVO.setNewTotalAmount(totalData.getNewAmount());
                dataVO.setRefuseTotalAmount(totalData.getRefuseAmount());
                dataVO.setReturnSupplementaryTotalAmount(totalData.getReturnSupplementaryAmount());

                return dataVO;
            }).collect(Collectors.toList());

            return ResponseUtils.success(rows);
        } catch (ServiceException se) {
            log.error("查询门店审核-报表失败，查询时间粒度:{},start:{},end:{},error message:{}", "day", start, end, se);
            return ResponseUtils.failure("查询门店审核-报表失败,请及时联系管理员！！");
        }
    }

    @Override
    public MessageTO getStoreData(String start, String end) {
        try {
            List<BusinessStoreCheckDay> data = businessStoreCheckDayDao.createQuery()
                    .andBetween(BiCommon.BIZ_DATE_COL, start, end)
                    .desc(BiCommon.BIZ_DATE_COL)
                    .select();
            List<BusinessStoreCheckDay> total = businessStoreCheckDayDao.selectTotalByBizDate(start, end);
            Map<String, BusinessStoreCheckDay> totalMap = total.stream().collect(Collectors.toMap(BusinessStoreCheckDay::getBizDate, e -> e));

            List<BusinessStoreCheckVO> rows = data.stream().map(e -> {
                BusinessStoreCheckVO dataVO = new BusinessStoreCheckVO();
                dataVO.setDate(e.getBizDate());
                dataVO.setApplyAmount(e.getApplyAmount());
                dataVO.setAccessAmount(e.getAccessAmount());
                dataVO.setAccessApplyAmount(e.getAccessApplyAmount());
                dataVO.setAccessFirstTrialAmount(e.getAccessFirstTrialAmount());
                dataVO.setActivationAmount(e.getActivationAmount());
                dataVO.setApplyApprovedAmount(e.getApplyApprovedAmount());
                dataVO.setApprovalRefuseAmount(e.getApprovalRefuseAmount());
                dataVO.setCloseAmount(e.getCloseAmount());
                dataVO.setFirstTrialAmount(e.getFirstTrialAmount());
                dataVO.setNewAmount(e.getNewAmount());
                dataVO.setRefuseAmount(e.getRefuseAmount());
                dataVO.setReturnSupplementaryAmount(e.getReturnSupplementaryAmount());

                BusinessStoreCheckDay totalData = totalMap.get(e.getBizDate());
                dataVO.setApplyTotalAmount(totalData.getApplyAmount());
                dataVO.setAccessTotalAmount(totalData.getAccessAmount());
                dataVO.setAccessApplyTotalAmount(totalData.getAccessApplyAmount());
                dataVO.setAccessFirstTrialTotalAmount(totalData.getAccessFirstTrialAmount());
                dataVO.setActivationTotalAmount(totalData.getActivationAmount());
                dataVO.setApplyApprovedTotalAmount(totalData.getApplyApprovedAmount());
                dataVO.setApprovalRefuseTotalAmount(totalData.getApprovalRefuseAmount());
                dataVO.setCloseTotalAmount(totalData.getCloseAmount());
                dataVO.setFirstTrialTotalAmount(totalData.getFirstTrialAmount());
                dataVO.setNewTotalAmount(totalData.getNewAmount());
                dataVO.setRefuseTotalAmount(totalData.getRefuseAmount());
                dataVO.setReturnSupplementaryTotalAmount(totalData.getReturnSupplementaryAmount());

                return dataVO;
            }).collect(Collectors.toList());

            return ResponseUtils.success(rows);
        } catch (ServiceException se) {
            log.error("查询门店审核-报表失败，查询时间粒度:{},start:{},end:{},error message:{}", "day", start, end, se);
            return ResponseUtils.failure("查询门店审核-报表失败,请及时联系管理员！！");
        }
    }
}
