package com.platform.report.service.impl;

import com.platform.report.bean.*;
import com.platform.report.common.constant.BiCommon;
import com.platform.report.common.entity.MessageTO;
import com.platform.report.common.utils.BusinessComputeUtils;
import com.platform.report.common.utils.DigitUtils;
import com.platform.report.common.utils.ResponseUtils;
import com.platform.report.controller.vo.*;
import com.platform.report.dao.*;
import com.platform.report.service.PopularizeService;
import com.platform.spring.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Log4j2
public class PopularizeServiceImpl implements PopularizeService {
    @Resource
    private BusinessPopuChannelDayDao businessPopuChannelDayDao;
    @Resource
    private BusinessPopuAgeDao businessPopuAgeDao;
    @Resource
    private BusinessPopuGenderDao businessPopuGenderDao;
    @Resource
    private BusinessPopuCustomerLevelDao businessPopuCustomerLevelDao;
    @Resource
    private BusinessPopuEducationDao businessPopuEducationDao;
    @Resource
    private BusinessPopuLoanPeriodDao businessPopuLoanPeriodDao;
    @Resource
    private BusinessPopuMobileModelDao businessPopuMobileModelDao;
    @Resource
    private BusinessPopuProvinceDao businessPopuProvinceDao;
    @Resource
    private BusinessPopuStreamApplyDayDao businessPopuStreamApplyDayDao;
    @Resource
    private BusinessPopuStreamApplyHourDao businessPopuStreamApplyHourDao;
    @Resource
    private BusinessPopuStreamConsumeDayDao businessPopuStreamConsumeDayDao;

    @Resource
    private BusinessApplyWithdrawSumDao businessApplyWithdrawSumDao;


    @Override
    public MessageTO getChannelReport(String start, String end) {
        //参数处理
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            log.info("查询参数异常，start:{},end:{}", start, end);
            return ResponseUtils.failure("查询推广引流参数异常，start:" + start + ",end" + end);
        }

        //查詢數據
        List<BusinessPopuChannelDay> data;
        List<BusinessApplyWithdrawSum> total;
        try {
            data = businessPopuChannelDayDao.createQuery()
                    .andBetween(BiCommon.BIZ_DATE_COL, start, end)
                    .select();
            //获取累计量
            total = businessApplyWithdrawSumDao.createQuery()
                    .andBetween(BiCommon.BIZ_DATE_COL, start, end)
                    .select();
        } catch (ServiceException se) {
            log.error("查询推广渠道报告失败，查询时间粒度:{},start:{},end:{},error message:{}", "day", start, end, se);
            return ResponseUtils.failure("查询推广渠道报告失败,请及时联系管理员！！");
        }

        //處理結果
        Map<String, BusinessApplyWithdrawSum> totalMap = total.stream().collect(Collectors.toMap(e -> e.getBizDate() + e.getChannel() + e.getCategory(), e -> e));
        List<BusinessPopularizeChannelReportVO> rows = data.stream().map(e -> copyBusinessPopularizeChannelReportVO(e, totalMap)).collect(Collectors.toList());

        return ResponseUtils.success(rows);

    }


    @Override
    public List<Map<String, Object>> getMobileModelSummary(String channel, String start, String end) {
        String newChannel = null;
        if (StringUtils.isNotBlank(channel)) {
            newChannel = channel.trim();
        }

        List<Map<String, Object>> rows = businessPopuMobileModelDao.selectTotal(start.trim(), end.trim(), newChannel);

        return rows;
    }

    @Override
    public List<Map<String, Object>> getAgeSummary(String channel, String start, String end) {

        String newChannel = null;
        if (StringUtils.isNotBlank(channel)) {
            newChannel = channel.trim();
        }


        List<Map<String, Object>> rows = businessPopuAgeDao.selectTotal(start.trim(), end.trim(), newChannel);

        return rows;

    }

    @Override
    public List<Map<String, Object>> getGenderSummary(String channel, String start, String end) {
        String newChannel = null;
        if (StringUtils.isNotBlank(channel)) {
            newChannel = channel.trim();
        }

        List<Map<String, Object>> rows = businessPopuGenderDao.selectTotal(start.trim(), end.trim(), newChannel);

        return rows;

    }

    @Override
    public List<Map<String, Object>> getProvinceSummary(String channel, String start, String end) {
        String newChannel = null;
        if (StringUtils.isNotBlank(channel)) {
            newChannel = channel.trim();
        }

        List<Map<String, Object>> rows = businessPopuProvinceDao.selectTotal(start.trim(), end.trim(), newChannel);

        return rows;

    }

    @Override
    public List<Map<String, Object>> getLoanPeriodSummary(String channel, String start, String end) {
        String newChannel = null;
        if (StringUtils.isNotBlank(channel)) {
            newChannel = channel.trim();
        }

        List<Map<String, Object>> rows = businessPopuLoanPeriodDao.selectTotal(start.trim(), end.trim(), newChannel);

        return rows;

    }

    @Override
    public List<Map<String, Object>> getEduSummary(String channel, String start, String end) {
        String newChannel = null;
        if (StringUtils.isNotBlank(channel)) {
            newChannel = channel.trim();
        }

        List<Map<String, Object>> rows = businessPopuEducationDao.selectTotal(start.trim(), end.trim(), newChannel);


        return rows;

    }

    @Override
    public List<Map<String, Object>> getCustomerLevelSummary(String channel, String start, String end) {
        String newChannel = null;
        if (StringUtils.isNotBlank(channel)) {
            newChannel = channel.trim();
        }

        List<Map<String, Object>> rows = businessPopuCustomerLevelDao.selectTotal(start.trim(), end.trim(), newChannel);

        return rows;

    }

    @Override
    public MessageTO getStreamApplyDataByDay(String start, String end) {
        //参数处理
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            log.info("查询参数异常，start:{},end:{}", start, end);
            return ResponseUtils.failure("查询推广引流参数异常失败，start:" + start + ",end" + end);
        }

        //查询数据
        List<BusinessPopuStreamApplyDay> data;
        try {
            data = businessPopuStreamApplyDayDao.createQuery()
                    .andBetween(BiCommon.BIZ_DATE_COL, start, end)
                    .orderBy("biz_date desc,register_amount desc").select();

        } catch (ServiceException se) {
            log.error("查询推广引流报告失败，查询时间粒度:{},start:{},end:{},error message:{}", "day", start, end, se);
            return ResponseUtils.failure("查询推广引流报告失败,请及时联系管理员！！");
        }

        //处理结果
        List<BusinessPopularizeReportVO> rows = data.stream().map(e -> {
            BusinessPopularizeReportVO dataVO = new BusinessPopularizeReportVO();
            dataVO.setDate(e.getBizDate());
            dataVO.setChannel(e.getChannel());
            dataVO.setOsType(e.getTarget());
            dataVO.setRegisterAmount(e.getRegisterAmount());
            dataVO.setRegisterIdentifiedAmount(e.getRegisterIdentified());
            dataVO.setApplyAmount(e.getAppliedAmount());
            dataVO.setApplyApprovedAmount(e.getApplyPass());
            dataVO.setApplyRefuseAmount(e.getApplyRefuse());
            dataVO.setMachineRefuseAmount(e.getMachineRefuse());
            dataVO.setWithdrawPassAmount(e.getWithdrawAmount());
            return dataVO;
        }).collect(Collectors.toList());

        return ResponseUtils.success(rows);
    }

    @Override
    public MessageTO getStreamChannelByHour(String start, String end) {
        try {
            BusinessPopularizeDataVO reportVO = new BusinessPopularizeDataVO();
            //获取上一个时间段的开始时间
            String preStart = String.valueOf(Long.valueOf(start) - 1);

            //汇总数据
            //1.申请报告
            List<BusinessPopuStreamApplyHour> reportList = businessPopuStreamApplyHourDao.createQuery().andBetween(BiCommon.BIZ_DATE_COL, start, end).desc("id").select();
            List<BusinessPopularizeReportVO> reportVOList = reportList.stream()
                    .map(this::copyChannelPopularizeReportVO)
                    .collect(Collectors.toList());
            reportVO.setReportVOList(reportVOList);

            //2.申请各环节数量及环比
            List<BusinessPopuStreamApplyHour> popularizeRatios = businessPopuStreamApplyHourDao.selectRegisterRatio(preStart, end);
            Map<Long, BusinessPopuStreamApplyHour> popularizeRatiosMap = popularizeRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()))
                    .collect(Collectors.toMap(e -> Long.valueOf(e.getBizDate()), e -> e));

            List<BusinessPopularizeChainRatioVO> popularizeChainRatios = popularizeRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()) && BusinessComputeUtils.validBizDate(e.getBizDate(), start))
                    .map(e -> copyPopularizeChainRatioVO(e, popularizeRatiosMap))
                    .collect(Collectors.toList());
            reportVO.setPopularizeChainRatios(popularizeChainRatios);

            //3.渠道申请量占比及数量环比（图）
            List<BusinessPopuStreamApplyHour> channelRatios = businessPopuStreamApplyHourDao.selectChannelRatio(preStart, end);
            //求同一时间段的所有渠道的注册量，用于求某渠道注册量占比
            Map<String, Long> totalChannel = channelRatios.stream().collect(Collectors.groupingBy(BusinessPopuStreamApplyHour::getBizDate, Collectors.summingLong(BusinessPopuStreamApplyHour::getRegisterAmount)));
            //把channelRatios转换成bizDate+channel为key的map,用于求上一渠道的申请量
            Map<String, BusinessPopuStreamApplyHour> channelRatioMap = channelRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()) && StringUtils.isNotBlank(e.getChannel()))
                    .collect(Collectors.toMap(e -> (e.getBizDate() + e.getChannel()), e -> e));

            List<BusinessPopularizeChannelChainRatioVO> channelApplyChainRatioVOList = channelRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()) && StringUtils.isNotBlank(e.getChannel()) && BusinessComputeUtils.validBizDate(e.getBizDate(), start))
                    .map(e -> copyChannelPopularizeChainRatioVO(e, channelRatioMap, totalChannel))
                    .collect(Collectors.toList());
            reportVO.setChannelPopularizeChainRatios(channelApplyChainRatioVOList);

            return ResponseUtils.success(reportVO);
        } catch (ServiceException se) {
            log.error("查询推广引流报告失败，查询时间粒度:{},start:{},end:{},error message:{}", "day", start, end, se);
            return ResponseUtils.failure("查询推广引流报告失败，请及时联系管理员！！");
        }
    }


    @Override
    public MessageTO getStreamConsumptionData(String channel, String start, String end) {
        //参数处理
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            log.info("查询参数异常，start:{},end:{}", start, end);
            return ResponseUtils.failure("查询推广引流参数异常，start:" + start + ",end" + end);
        }

        //查询数据
        List<BusinessPopuStreamConsumeDay> data;
        try {
            data = businessPopuStreamConsumeDayDao.selectGroupByChannel(channel, start, end);
        } catch (ServiceException se) {
            log.error("查询推广引流消费数据失败，查询时间:start:{},end:{},error message:{}", start, end, se);
            return ResponseUtils.failure("查询推广引流消费数据失败,请及时联系管理员！！");
        }

        //处理结果
        List<BusinessPopularizeConsumeVO> rows = data.stream()
                .map(e -> {
                    BusinessPopularizeConsumeVO consumeVO = new BusinessPopularizeConsumeVO();
                    consumeVO.setChannel(e.getChannel());
                    consumeVO.setPaymentAmount(e.getPaymentAmount());
                    consumeVO.setPaymentSum(e.getPaymentSum());
                    consumeVO.setWithdrawAmount(e.getWithdrawAmount());
                    consumeVO.setWithdrawSum(e.getWithdrawSum());
                    return consumeVO;
                })
                .collect(Collectors.toList());

        return ResponseUtils.success(rows);
    }

    @Override
    public MessageTO getStreamBrandData(String channel, String start, String end) {
        //参数处理
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            log.info("查询参数异常，start:{},end:{}", start, end);
            return ResponseUtils.failure("查询推广引流参数异常，start:" + start + ",end" + end);
        }

        //查詢數據
        List<BusinessPopuStreamConsumeDay> data;
        try {
            data = businessPopuStreamConsumeDayDao.selectGroupByBrandName(channel, start, end);

        } catch (ServiceException se) {
            log.error("查询推广引流品牌数据失败，查询时间:start:{},end:{},error message:{}", start, end, se);
            return ResponseUtils.failure("查询推广引流品牌数据失败,请及时联系管理员！！");
        }

        //處理結果
        List<BusinessPopularizeBrandVO> rows = data.stream()
                .map(e -> {
                    BusinessPopularizeBrandVO brandVO = new BusinessPopularizeBrandVO();
                    brandVO.setBrandName(e.getBrandName());
                    brandVO.setConsumptionSum(e.getPaymentSum() + e.getWithdrawSum());
                    return brandVO;
                })
                .collect(Collectors.toList());
        //取前十
        List<BusinessPopularizeBrandVO> result = rows.size() > 10 ? rows.subList(0, 10) : rows;

        return ResponseUtils.success(result);
    }

    @Override
    public MessageTO getStreamStoreData(String channel, String start, String end) {
        //参数处理
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            log.info("查询参数异常，start:{},end:{}", start, end);
            return ResponseUtils.failure("查询推广引流门店参数异常，start:" + start + ",end" + end);
        }

        //查詢數據
        List<BusinessPopuStreamConsumeDay> data;
        try {
            data = businessPopuStreamConsumeDayDao.selectGroupByRNameAndStoreName(channel, start, end);
        } catch (ServiceException se) {
            log.error("查询推广引流门店数据失败，查询时间粒度:start:{},end:{},error message:{}", start, end, se);
            return ResponseUtils.failure("查询推广引流门店数据失败,请及时联系管理员！！");
        }

        //處理結果
        List<BusinessPopularizeStoreVO> rows = data.stream()
                .map(e -> {
                    BusinessPopularizeStoreVO storeVO = new BusinessPopularizeStoreVO();
                    storeVO.setRName(e.getRName());
                    storeVO.setStoreName(e.getStoreName());
                    storeVO.setConsumptionAmount(e.getPaymentAmount() + e.getWithdrawAmount());
                    storeVO.setConsumptionSum(e.getPaymentSum() + e.getWithdrawSum());
                    return storeVO;
                })
                .collect(Collectors.toList());

        return ResponseUtils.success(rows);
    }

    private BusinessPopularizeReportVO copyChannelPopularizeReportVO(BusinessPopuStreamApplyHour e) {
        BusinessPopularizeReportVO businessPopularizeReportVO = new BusinessPopularizeReportVO();
        businessPopularizeReportVO.setDate(e.getBizDate());
        businessPopularizeReportVO.setChannel(e.getChannel());
        businessPopularizeReportVO.setRegisterAmount(e.getRegisterAmount());
        businessPopularizeReportVO.setRegisterIdentifiedAmount(e.getRegisterIdentified());
        businessPopularizeReportVO.setApplyAmount(e.getAppliedAmount());
        businessPopularizeReportVO.setApplyApprovedAmount(e.getApplyPass());
        businessPopularizeReportVO.setApplyRefuseAmount(e.getApplyRefuse());
        businessPopularizeReportVO.setMachineRefuseAmount(e.getMachineRefuse());
        businessPopularizeReportVO.setWithdrawPassAmount(e.getWithdrawAmount());
        return businessPopularizeReportVO;
    }


    private BusinessPopularizeChainRatioVO copyPopularizeChainRatioVO(BusinessPopuStreamApplyHour e, Map<Long, BusinessPopuStreamApplyHour> dataMap) {
        //获取前一个对象
        Long preIndex = Long.valueOf(e.getBizDate()) - 1;
        BusinessPopuStreamApplyHour preObj = dataMap.get(preIndex);

        //汇总结果
        BusinessPopularizeChainRatioVO chainRatioVO = new BusinessPopularizeChainRatioVO();
        chainRatioVO.setRegisterAmount(DigitUtils.getValidValue(e.getRegisterAmount()));
        chainRatioVO.setRegisterIdentifiedAmount(DigitUtils.getValidValue(e.getRegisterIdentified()));
        chainRatioVO.setAppliedAmount(DigitUtils.getValidValue(e.getAppliedAmount()));
        chainRatioVO.setApplyPassAmount(DigitUtils.getValidValue(e.getApplyPass()));
        chainRatioVO.setMachineRefuseAmount(DigitUtils.getValidValue(e.getMachineRefuse()));
        chainRatioVO.setWithdrawPassAmount(DigitUtils.getValidValue(e.getWithdrawAmount()));
        chainRatioVO.setDate(e.getBizDate());

        //计算环比
        if (null == preObj) {
            chainRatioVO.setRegisterChainRatio(0.0);
            chainRatioVO.setRegisterIdentifiedChainRatio(0.0);
            chainRatioVO.setAppliedChainRatio(0.0);
            chainRatioVO.setApplyPassChainRatio(0.0);
            chainRatioVO.setMachineRefuseChainRatio(0.0);
            chainRatioVO.setWithdrawPassChainRatio(0.0);
        } else {
            chainRatioVO.setRegisterChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getRegisterAmount(), e.getRegisterAmount()));
            chainRatioVO.setRegisterIdentifiedChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getRegisterIdentified(), e.getRegisterIdentified()));
            chainRatioVO.setAppliedChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getAppliedAmount(), preObj.getAppliedAmount()));
            chainRatioVO.setApplyPassChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getApplyPass(), e.getApplyPass()));
            chainRatioVO.setMachineRefuseChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getMachineRefuse(), e.getMachineRefuse()));
            chainRatioVO.setWithdrawPassChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getWithdrawAmount(), e.getWithdrawAmount()));
        }
        return chainRatioVO;
    }

    private BusinessPopularizeChannelChainRatioVO copyChannelPopularizeChainRatioVO(BusinessPopuStreamApplyHour e, Map<String, BusinessPopuStreamApplyHour> dataMap,
                                                                                    Map<String, Long> totalChannel) {
        //获取前一个对象
        Long preIndex = Long.valueOf(e.getBizDate()) - 1;
        BusinessPopuStreamApplyHour preObj = dataMap.get(preIndex.toString() + e.getChannel());

        //汇总结果
        BusinessPopularizeChannelChainRatioVO channelChainRatioVO = new BusinessPopularizeChannelChainRatioVO();
        channelChainRatioVO.setDate(e.getBizDate());
        channelChainRatioVO.setChannel(e.getChannel());
        //计算在当前时间段，某渠道的注册率
        Double currChannelRatio = BusinessComputeUtils.computeRatio(e.getRegisterAmount(), totalChannel.get(e.getBizDate()));
        channelChainRatioVO.setRatio(currChannelRatio);
        //计算环比
        if (null == preObj) {
            channelChainRatioVO.setChainRatio(0.0);
        } else {
            Double preChannelRatio = BusinessComputeUtils.computeRatio(preObj.getRegisterAmount(), totalChannel.get(preObj.getBizDate()));
            channelChainRatioVO.setChainRatio(BusinessComputeUtils.computeChainRatio(preChannelRatio, currChannelRatio));
        }
        return channelChainRatioVO;
    }


    private BusinessPopularizeChannelReportVO copyBusinessPopularizeChannelReportVO(BusinessPopuChannelDay e, Map<String, BusinessApplyWithdrawSum> totalMap) {
        BusinessPopularizeChannelReportVO dataVO = new BusinessPopularizeChannelReportVO();
        dataVO.setDate(e.getBizDate());
        dataVO.setChannel(e.getChannel());
        //注册信息
        dataVO.setRegisterAmount(e.getRegisterAmount());
        dataVO.setIdentifiedAmount(e.getIdentifiedAmount());
        dataVO.setApplyAmount(e.getAppliedAmount());
        dataVO.setApplyApprovedAmount(e.getApplyPass());
        dataVO.setMachinePassAmount(e.getMachinePass());
        dataVO.setApplyRefusedAmount(e.getApplyRefuse());
        dataVO.setMachineRefuseAmount(e.getMachineRefuse());
        dataVO.setApprovedCreditSum(e.getApprovalSum());

        //提现信息
        dataVO.setWithdrawAmount(e.getWithdrawAmount());
        dataVO.setWithdrawSum(e.getWithdrawSum());
        dataVO.setRepaymentWithdrawAmount(e.getRepaymentWithdrawAmount());
        dataVO.setApprovedWithdrawSum(e.getApprovedWithdrawSum());

        //累计提现信息
        BusinessApplyWithdrawSum withdrawTotal = totalMap.get(e.getBizDate() + e.getChannel() + BiCommon.WITHDRAW_SUC_CATEGORY);
        if (withdrawTotal == null) {
            dataVO.setTotalWithdrawTimes(0L);
            dataVO.setTotalWithdrawSum(0.0);
        } else {
            dataVO.setTotalWithdrawTimes(withdrawTotal.getTotalSuccessfulTimes());
            dataVO.setTotalWithdrawAmount(withdrawTotal.getTotalSuccessfulAmount());
            dataVO.setTotalWithdrawSum(withdrawTotal.getTotalSuccessfulSum());
        }
        BusinessApplyWithdrawSum applyTotal = totalMap.get(e.getBizDate() + e.getChannel() + BiCommon.APPLY_SUC_CATEGORY);
        if (withdrawTotal == null) {
            dataVO.setApplyApprovedTotalAmount(0L);
        } else {
            dataVO.setApplyApprovedTotalAmount(applyTotal.getTotalSuccessfulAmount());
        }

        //逾期信息
        dataVO.setFirstOverdueAmount(0L);
        dataVO.setM1OverdueAmount(0L);
        dataVO.setM2OverdueAmount(0L);
        dataVO.setM3OverdueAmount(0L);
        dataVO.setBadLogAmount(0L);
        return dataVO;
    }

}


