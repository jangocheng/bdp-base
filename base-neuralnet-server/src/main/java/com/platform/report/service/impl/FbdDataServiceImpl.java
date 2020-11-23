package com.platform.report.service.impl;

import com.platform.report.bean.FbdConsumeMoney;
import com.platform.report.common.utils.DateUtils;
import com.platform.report.common.utils.DigitUtils;
import com.platform.report.controller.vo.RealTimeConsumptionVO;
import com.platform.report.dao.FbdConsumeMoneyDao;
import com.platform.report.service.FbdDataService;
import com.platform.spring.bean.MessageTO;
import com.platform.spring.exception.ServiceException;
import com.platform.spring.util.ResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wlhbdp
 * @create 2020/03/26 15:56
 */
@Service
@Log4j2
public class FbdDataServiceImpl implements FbdDataService {
    /**
     * amtType金额类型为01--提现
     */
    private final static String WITHDRAW_TYPE = "01";
    /**
     * amtType金额类型为02--信用支付
     */
    private final static String CREDIT_TYPE = "02";
    /**
     * amtType金额类型为03--薪资支付
     */
    private final static String SALARY_TYPE = "03";

    @Resource
    private FbdConsumeMoneyDao fbdConsumeMoneyDao;

    @Override
    public MessageTO getDailyRealTimeConsumption(){
        try {
            //从数据库查询相关数据
            String bizDate = DateUtils.getBizDate(System.currentTimeMillis());
            List<FbdConsumeMoney> moneyList = fbdConsumeMoneyDao.createQuery()
                    .andEq("biz_date", bizDate)
                    .desc("biz_hour").select();

            //对biz_hour分组,并把数据转换成type->amount的map,最终结果Map<Integer,Map<String,Double>>
            Map<Integer, Map<String, Double>> data = moneyList.stream().collect(Collectors.groupingBy(FbdConsumeMoney::getBizHour, Collectors.toMap(FbdConsumeMoney::getAmtType, FbdConsumeMoney::getAmount)));


            //汇总数据
            List<RealTimeConsumptionVO> consumptionVOS = new ArrayList<>();
            Integer lower = moneyList.get(moneyList.size() - 1).getBizHour();
            Integer upper = moneyList.get(0).getBizHour();
            for (int i = upper; i >= lower; i--) {
                RealTimeConsumptionVO consumptionVO;
                Map<String, Double> curr = data.get(i);
                if (i == lower) {
                    consumptionVO = copyRealTimeConsumptionVO(null, curr);
                } else {
                    Map<String, Double> pre = data.get(i - 1);
                    consumptionVO = copyRealTimeConsumptionVO(pre, curr);
                }
                consumptionVO.setBizHour(i);

                consumptionVOS.add(consumptionVO);
            }


            //过滤，取前五个
            List<RealTimeConsumptionVO> result = consumptionVOS.size() > 5 ? consumptionVOS.subList(0, 5) : consumptionVOS;

            return ResponseUtil.genSuccessResult().setRows(result);

        } catch (ServiceException se) {
            log.error("每日实时消费金额查询失败", se);
            return ResponseUtil.genFailMessage("每日实时消费金额查询失败");
        }
    }

    private RealTimeConsumptionVO copyRealTimeConsumptionVO(Map<String, Double> pre, Map<String, Double> curr) {
        RealTimeConsumptionVO consumptionVO = new RealTimeConsumptionVO();
        consumptionVO.setWithdrawSum(DigitUtils.getValidValue(curr.get(WITHDRAW_TYPE)));
        consumptionVO.setCreditPaymentSum(DigitUtils.getValidValue(curr.get(CREDIT_TYPE)));
        consumptionVO.setSalaryPaymentSum(DigitUtils.getValidValue(curr.get(SALARY_TYPE)));

        //获取每小时对应的量
        if (null == pre) {
            consumptionVO.setWithdraw(DigitUtils.getValidValue(curr.get(WITHDRAW_TYPE)));
            consumptionVO.setCreditPayment(DigitUtils.getValidValue(curr.get(CREDIT_TYPE)));
            consumptionVO.setSalaryPayment(DigitUtils.getValidValue(curr.get(SALARY_TYPE)));
        } else {
            consumptionVO.setWithdraw(DigitUtils.getValidValue(curr.get(WITHDRAW_TYPE)) - DigitUtils.getValidValue(pre.get(WITHDRAW_TYPE)));
            consumptionVO.setCreditPayment(DigitUtils.getValidValue(curr.get(CREDIT_TYPE)) - DigitUtils.getValidValue(pre.get(CREDIT_TYPE)));
            consumptionVO.setSalaryPayment(DigitUtils.getValidValue(curr.get(SALARY_TYPE)) - DigitUtils.getValidValue(pre.get(SALARY_TYPE)));
        }
        return consumptionVO;
    }

}
