package com.platform.report.service.impl;

import com.platform.report.bean.BusinessApplyDay;
import com.platform.report.bean.BusinessApplyHour;
import com.platform.report.bean.BusinessRegisterDay;
import com.platform.report.bean.BusinessRegisterHour;
import com.platform.report.common.BiCommon;
import com.platform.report.common.entity.MessageTO;
import com.platform.report.common.utils.BusinessComputeUtils;
import com.platform.report.common.utils.DigitUtils;
import com.platform.report.common.utils.ResponseUtils;
import com.platform.report.controller.vo.*;
import com.platform.report.dao.BusinessApplyDayDao;
import com.platform.report.dao.BusinessApplyHourDao;
import com.platform.report.dao.BusinessRegisterDayDao;
import com.platform.report.dao.BusinessRegisterHourDao;
import com.platform.report.service.RegisterApplyService;
import com.platform.spring.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wlhbdp
 */
@Service
@Log4j2
public class RegisterApplyServiceImpl implements RegisterApplyService {
    @Resource
    private BusinessApplyDayDao businessApplyDayDao;
    @Resource
    private BusinessApplyHourDao businessApplyHourDao;
    @Resource
    private BusinessRegisterHourDao businessRegisterHourDao;
    @Resource
    private BusinessRegisterDayDao businessRegisterDayDao;


    @Override
    public MessageTO getApplyChannelByHour(String start, String end) {
        try {
            BusinessApplyDataVO reportVO = new BusinessApplyDataVO();
            //获取上一个时间段的开始时间
            String preStart = String.valueOf(Long.valueOf(start) - 1);

            //汇总数据
            //1.申请报告
            List<BusinessApplyHour> reportList = businessApplyHourDao.createQuery().andBetween(BiCommon.BIZ_DATE_COL, start, end).desc("id").select();
            List<BusinessApplyReportVO> reportVOList = reportList.stream()
                    .map(this::copyChannelApplyReportVO)
                    .collect(Collectors.toList());
            reportVO.setReport(reportVOList);

            //2.申请各环节数量及环比
            List<BusinessApplyHour> applyRatios = businessApplyHourDao.selectRegisterRatio(preStart, end);
            Map<Long, BusinessApplyHour> applyRatiosMap = applyRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()))
                    .collect(Collectors.toMap(e -> Long.valueOf(e.getBizDate()), e -> e));

            List<BusinessApplyChainRatioVO> popularizeChainRatios = applyRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()) && BusinessComputeUtils.validBizDate(e.getBizDate(), start))
                    .map(e -> copyApplyChainRatioVO(e, applyRatiosMap))
                    .collect(Collectors.toList());
            reportVO.setApplyChainRatios(popularizeChainRatios);

            //3.渠道申请量占比及数量环比（图）
            List<BusinessApplyHour> channelRatios = businessApplyHourDao.selectChannelRatio(preStart, end);
            //求同一时间段的所有渠道的注册量，用于求某渠道注册量占比
            Map<String, Long> totalChannel = channelRatios.stream().collect(Collectors.groupingBy(BusinessApplyHour::getBizDate, Collectors.summingLong(BusinessApplyHour::getAppliedAmount)));
            //把channelRatios转换成bizDate+channel为key的map,用于求上一渠道的申请量
            Map<String, BusinessApplyHour> channelRatioMap = channelRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()) && StringUtils.isNotBlank(e.getChannel()))
                    .collect(Collectors.toMap(e -> (e.getBizDate() + e.getChannel()), e -> e));

            List<BusinessApplyChannelChainRatioVO> businessApplyChannelChainRatioVOList = channelRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()) && StringUtils.isNotBlank(e.getChannel()) && BusinessComputeUtils.validBizDate(e.getBizDate(), start))
                    .map(e -> copyChannelApplyChainRatioVO(e, channelRatioMap, totalChannel))
                    .collect(Collectors.toList());
            reportVO.setChannelApplyChainRatios(businessApplyChannelChainRatioVOList);

            return ResponseUtils.success(reportVO);
        } catch (ServiceException se) {
            log.error("查询申请报告失败，查询时间粒度:{},start:{},end:{},error message:{}", "day", start, end, se);
            return ResponseUtils.failure("查询申请报告失败，请及时联系管理员！！");
        }
    }

    @Override
    public MessageTO getApplyDataByDay(String start, String end) {
        //检查参数
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            log.info("查询参数异常，start:{},end:{}", start, end);
            return ResponseUtils.failure("查询申请报告参数异常，start:" + start + ",end" + end);
        }

        //查询数据
        List<BusinessApplyDay> data;
        try {
            data = businessApplyDayDao.createQuery()
                    .andBetween(BiCommon.BIZ_DATE_COL, start, end)
                    .orderBy("biz_date desc,applied_amount desc").select();
        } catch (ServiceException se) {
            log.error("查询申请报告失败，查询时间粒度:{},start:{},end:{},error message:{}", "day", start, end, se);
            return ResponseUtils.failure("查询申请报告失败，请及时联系管理员！！");
        }

        //处理结果
        List<BusinessApplyReportVO> rows = data.stream().map(e -> {
            BusinessApplyReportVO reportVO = new BusinessApplyReportVO();
            reportVO.setDate(e.getBizDate());
            reportVO.setChannel(e.getChannel());
            reportVO.setOsType(e.getTarget());
            reportVO.setCustomerType(e.getCustomerType());
            reportVO.setApplyAmount(e.getAppliedAmount());
            reportVO.setApplyApprovedAmount(e.getApplyPass());
            reportVO.setApplyCancelledAmount(e.getApplyCancel());
            reportVO.setApplyApprovingAmount(e.getApplyIng());
            reportVO.setApplyRefusedAmount(e.getApplyRefuse());
            reportVO.setApplyReturnAmount(e.getApplyReturn());
            reportVO.setMachinePassAmount(e.getMachinePass());
            reportVO.setMachineRefuseAmount(e.getMachineRefuse());
            reportVO.setServiceValidAmount(e.getServiceValid());
            reportVO.setApprovedCreditSum(e.getApprovalSum());
            return reportVO;
        }).collect(Collectors.toList());

        return ResponseUtils.success(rows);
    }

    @Override
    public MessageTO getRegisterDataByDay(String start, String end) {
        //参数处理
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            log.info("查询参数异常，start:{},end:{}", start, end);
            return ResponseUtils.failure("查询注册报告参数异常，start:" + start + ",end" + end);
        }

        //查询数据
        List<BusinessRegisterDay> data;
        try {
            data = businessRegisterDayDao.createQuery().andBetween(BiCommon.BIZ_DATE_COL, start, end)
                    .orderBy("biz_date desc,register_amount desc").select();

        } catch (ServiceException se) {
            log.error("查询注册报告失败，查询时间粒度:{},start:{},end:{},error message:{}", "day", start, end, se);
            return ResponseUtils.failure("查询注册报告失败，请及时联系管理员！！");
        }

        //处理结果
        List<BusinessRegisterReportVO> rows = data.stream().map(e -> {
            BusinessRegisterReportVO dataVO = new BusinessRegisterReportVO();
            dataVO.setDate(e.getBizDate());
            dataVO.setChannel(e.getChannel());
            dataVO.setOsType(e.getTarget());
            dataVO.setRegisterAmount(e.getRegisterAmount());
            dataVO.setIdentifiedAmount(e.getRegisterIdentified());
            dataVO.setApplyAmount(e.getAppliedAmount());
            dataVO.setApplyApprovedAmount(e.getApplyPass());
            dataVO.setApplyApprovingAmount(e.getApplyIng());
            dataVO.setApplyCancelledAmount(e.getApplyCancel());
            dataVO.setApplyRefusedAmount(e.getApplyRefuse());
            return dataVO;
        }).collect(Collectors.toList());

        return ResponseUtils.success(rows);

    }

    @Override
    public MessageTO getRegisterChannelByHour(String start, String end) {
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            log.info("查询参数异常，start:{},end:{}", start, end);
            return ResponseUtils.failure("查询注册量失败，start:" + start + ",end" + end + "请及时联系管理员！！");
        }

        try {
            BusinessRegisterDataVO reportVO = new BusinessRegisterDataVO();
            //获取上一个时间段的开始时间
            String preStart = String.valueOf(Long.valueOf(start) - 1);

            //汇总数据
            //1.申请报告
            List<BusinessRegisterHour> reportList = businessRegisterHourDao.createQuery().andBetween(BiCommon.BIZ_DATE_COL, start, end).desc("id").select();
            List<BusinessRegisterReportVO> reportVOList = reportList.stream()
                    .map(this::copyChannelRegisterReportVO)
                    .collect(Collectors.toList());
            reportVO.setReportVOList(reportVOList);

            //2.申请各环节数量及环比
            List<BusinessRegisterHour> registerRatios = businessRegisterHourDao.selectRegisterRatio(preStart, end);
            Map<Long, BusinessRegisterHour> registerRatiosMap = registerRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()))
                    .collect(Collectors.toMap(e -> Long.valueOf(e.getBizDate()), e -> e));

            List<BusinessRegisterChainRatioVO> registerChainRatios = registerRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()) && BusinessComputeUtils.validBizDate(e.getBizDate(), start))
                    .map(e -> copyRegisterChainRatioVO(e, registerRatiosMap))
                    .collect(Collectors.toList());
            reportVO.setRegisterChainRatios(registerChainRatios);

            //3.渠道申请量占比及数量环比（图）
            List<BusinessRegisterHour> channelRatios = businessRegisterHourDao.selectChannelRatio(preStart, end);
            //求同一时间段的所有渠道的注册量，用于求某渠道注册量占比
            Map<String, Long> totalChannel = channelRatios.stream().collect(Collectors.groupingBy(BusinessRegisterHour::getBizDate, Collectors.summingLong(BusinessRegisterHour::getRegisterAmount)));
            //把channelRatios转换成bizDate+channel为key的map,用于求上一渠道的申请量
            Map<String, BusinessRegisterHour> channelRatioMap = channelRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()) && StringUtils.isNotBlank(e.getChannel()))
                    .collect(Collectors.toMap(e -> (e.getBizDate() + e.getChannel()), e -> e));

            List<BusinessRegisterChannelChainRatioVO> channelApplyChainRatioVOList = channelRatios.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getBizDate()) && StringUtils.isNotBlank(e.getChannel()) && BusinessComputeUtils.validBizDate(e.getBizDate(), start))
                    .map(e -> copyChannelRegisterChainRatioVO(e, channelRatioMap, totalChannel))
                    .collect(Collectors.toList());
            reportVO.setChannelRegisterChainRatios(channelApplyChainRatioVOList);

            return ResponseUtils.success(reportVO);
        } catch (ServiceException se) {
            log.error("查询注册报告失败，查询时间粒度:{},start:{},end:{},error message:{}", "day", start, end, se);
            return ResponseUtils.failure("查询注册报告失败，请及时联系管理员！！");
        }

    }


    @Override
    public MessageTO getRegisterChannels(String start, String end) {
        //查询数据
        List<BusinessRegisterDay> data;
        try {
            if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
                data = businessRegisterDayDao.selectChannelsByBizDate(start.trim(), end.trim());
            } else {
                data = businessRegisterDayDao.selectChannels();
            }
        } catch (ServiceException se) {
            log.error("根据注册量排序,查询所有注册渠道失败。");
            return ResponseUtils.failure("根据注册量排序,查询所有注册渠道失败。");
        }

        //处理结果
        List<String> channels = new ArrayList<>();
        for (BusinessRegisterDay registerData : data) {
            String channel = registerData.getChannel();
            if (!channels.contains(channel)) {
                channels.add(channel);
            }
        }

        return ResponseUtils.success(channels);
    }

    @Override
    public MessageTO getApplyChannels(String start, String end) {
        //查询数据
        List<BusinessApplyDay> data;
        try {
            if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
                data = businessApplyDayDao.selectChannelsByBizDate(start, end);
            } else {
                data = businessApplyDayDao.selectChannels();
            }
        } catch (ServiceException se) {
            log.error("根据申请进件量排序,查询所有注册渠道失败。");
            return ResponseUtils.failure("根据申请进件量排序,查询所有注册渠道失败。");
        }

        //处理结果
        List<String> channels = new ArrayList<>();
        for (BusinessApplyDay applyDay : data) {
            String channel = applyDay.getChannel();
            if (!channels.contains(channel)) {
                channels.add(channel);
            }
        }
        return ResponseUtils.success(channels);
    }

    private BusinessApplyReportVO copyChannelApplyReportVO(BusinessApplyHour e) {
        BusinessApplyReportVO businessApplyReportVO = new BusinessApplyReportVO();
        businessApplyReportVO.setDate(e.getBizDate());
        businessApplyReportVO.setChannel(e.getChannel());
        businessApplyReportVO.setOsType(e.getTarget());
        businessApplyReportVO.setCustomerType(e.getCustomerTypeName());
        businessApplyReportVO.setApplyAmount(e.getAppliedAmount());
        businessApplyReportVO.setApplyApprovedAmount(e.getApplyPass());
        businessApplyReportVO.setMachinePassAmount(e.getMachinePass());
        businessApplyReportVO.setApprovedCreditSum(e.getApprovalSum());
        businessApplyReportVO.setApplyApprovingAmount(e.getApplyIng());
        businessApplyReportVO.setServiceValidAmount(e.getServiceValid());
        businessApplyReportVO.setApplyRefusedAmount(e.getApplyRefuse());
        businessApplyReportVO.setMachineRefuseAmount(e.getMachineRefuse());
        businessApplyReportVO.setApplyCancelledAmount(e.getApplyCancel());
        businessApplyReportVO.setApplyReturnAmount(e.getApplyReturn());

        return businessApplyReportVO;
    }


    private BusinessApplyChainRatioVO copyApplyChainRatioVO(BusinessApplyHour e, Map<Long, BusinessApplyHour> dataMap) {
        //获取前一个对象
        Long preIndex = Long.valueOf(e.getBizDate()) - 1;
        BusinessApplyHour preObj = dataMap.get(preIndex);

        //汇总结果
        BusinessApplyChainRatioVO chainRatioVO = new BusinessApplyChainRatioVO();
        chainRatioVO.setApplyAmount(DigitUtils.getValidValue(e.getAppliedAmount()));
        chainRatioVO.setApplyApprovedAmount(DigitUtils.getValidValue(e.getApplyPass()));
        chainRatioVO.setApprovedCreditSum(DigitUtils.getValidValue(e.getApprovalSum()));
        chainRatioVO.setApplyApprovingAmount(DigitUtils.getValidValue(e.getApplyIng()));
        chainRatioVO.setDate(e.getBizDate());
        Double currPassRatio = BusinessComputeUtils.computeRatio(e.getApplyPass(), e.getAppliedAmount());
        chainRatioVO.setApplyPassRatio(currPassRatio);

        //计算环比
        if (null == preObj) {
            chainRatioVO.setApplyChainRatio(0.0);
            chainRatioVO.setApplyApprovedChainRatio(0.0);
            chainRatioVO.setApplyPassRatioChainRatio(0.0);
            chainRatioVO.setApprovedCreditSumChainRatio(0.0);
            chainRatioVO.setApplyApprovingChainRatio(0.0);
        } else {
            chainRatioVO.setApplyChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getAppliedAmount(), e.getAppliedAmount()));
            chainRatioVO.setApplyApprovedChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getApplyPass(), e.getApplyPass()));
            chainRatioVO.setApprovedCreditSumChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getApprovalSum(), e.getApprovalSum()));
            chainRatioVO.setApplyApprovingChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getApplyIng(), e.getApplyIng()));
            Double prePassRatio = BusinessComputeUtils.computeRatio(preObj.getApplyPass(), preObj.getAppliedAmount());
            chainRatioVO.setApplyPassRatioChainRatio(BusinessComputeUtils.computeChainRatio(prePassRatio, currPassRatio));
        }
        return chainRatioVO;
    }

    private BusinessApplyChannelChainRatioVO copyChannelApplyChainRatioVO(BusinessApplyHour e, Map<String, BusinessApplyHour> dataMap,
                                                                          Map<String, Long> totalChannel) {
        //获取前一个对象
        Long preIndex = Long.valueOf(e.getBizDate()) - 1;
        BusinessApplyHour preObj = dataMap.get(preIndex.toString() + e.getChannel());

        //汇总结果
        BusinessApplyChannelChainRatioVO channelChainRatioVO = new BusinessApplyChannelChainRatioVO();
        channelChainRatioVO.setDate(e.getBizDate());
        channelChainRatioVO.setChannel(e.getChannel());
        //计算在当前时间段，某渠道的注册率
        Double currChannelRatio = BusinessComputeUtils.computeRatio(e.getAppliedAmount(), totalChannel.get(e.getBizDate()));
        channelChainRatioVO.setRatio(currChannelRatio);
        //计算环比
        if (null == preObj) {
            channelChainRatioVO.setChainRatio(0.0);
        } else {
            Double preChannelRatio = BusinessComputeUtils.computeRatio(preObj.getAppliedAmount(), totalChannel.get(preObj.getBizDate()));
            channelChainRatioVO.setChainRatio(BusinessComputeUtils.computeChainRatio(preChannelRatio, currChannelRatio));
        }
        return channelChainRatioVO;
    }

    private BusinessRegisterReportVO copyChannelRegisterReportVO(BusinessRegisterHour reportHour) {
        BusinessRegisterReportVO channelApplyChainRatioVO = new BusinessRegisterReportVO();
        channelApplyChainRatioVO.setDate(reportHour.getBizDate());
        channelApplyChainRatioVO.setChannel(reportHour.getChannel());
        channelApplyChainRatioVO.setOsType(reportHour.getTarget());
        channelApplyChainRatioVO.setRegisterAmount(DigitUtils.getValidValue(reportHour.getRegisterAmount()));
        channelApplyChainRatioVO.setIdentifiedAmount(DigitUtils.getValidValue(reportHour.getRegisterIdentified()));
        channelApplyChainRatioVO.setApplyAmount(DigitUtils.getValidValue(reportHour.getAppliedAmount()));
        channelApplyChainRatioVO.setApplyApprovedAmount(DigitUtils.getValidValue(reportHour.getApplyPass()));
        channelApplyChainRatioVO.setApplyApprovingAmount(DigitUtils.getValidValue(reportHour.getApplyIng()));
        channelApplyChainRatioVO.setApplyCancelledAmount(DigitUtils.getValidValue(reportHour.getApplyCancel()));
        channelApplyChainRatioVO.setApplyRefusedAmount(DigitUtils.getValidValue(reportHour.getApplyRefuse()));

        return channelApplyChainRatioVO;
    }

    private BusinessRegisterChainRatioVO copyRegisterChainRatioVO(BusinessRegisterHour e, Map<Long, BusinessRegisterHour> dataMap) {
        //获取前一个对象
        Long preIndex = Long.valueOf(e.getBizDate()) - 1;
        BusinessRegisterHour preObj = dataMap.get(preIndex);

        //汇总结果
        BusinessRegisterChainRatioVO businessRegisterChainRatioVO = new BusinessRegisterChainRatioVO();
        businessRegisterChainRatioVO.setRegisterAmount(DigitUtils.getValidValue(e.getRegisterAmount()));
        businessRegisterChainRatioVO.setIdentifiedAmount(DigitUtils.getValidValue(e.getRegisterIdentified()));
        businessRegisterChainRatioVO.setApplyAmount(DigitUtils.getValidValue(e.getAppliedAmount()));
        businessRegisterChainRatioVO.setDate(e.getBizDate());
        Double currIdRatio = BusinessComputeUtils.computeRatio(e.getRegisterIdentified(), e.getRegisterAmount());
        businessRegisterChainRatioVO.setIdentifiedRatio(currIdRatio);
        Double currApplyPassRatio = BusinessComputeUtils.computeRatio(e.getApplyPass(), e.getAppliedAmount());
        businessRegisterChainRatioVO.setApplyPassRatio(currApplyPassRatio);
        //计算环比
        if (null == preObj) {
            businessRegisterChainRatioVO.setRegisterChainRatio(0.0);
            businessRegisterChainRatioVO.setIdentifiedChainRatio(0.0);
            businessRegisterChainRatioVO.setIdentifiedRatioChainRatio(0.0);
            businessRegisterChainRatioVO.setApplyChainRatio(0.0);
            businessRegisterChainRatioVO.setApplyPassRatioChainRatio(0.0);
        } else {
            businessRegisterChainRatioVO.setRegisterChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getRegisterAmount(), e.getRegisterAmount()));
            businessRegisterChainRatioVO.setIdentifiedChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getRegisterIdentified(), e.getRegisterIdentified()));
            businessRegisterChainRatioVO.setApplyChainRatio(BusinessComputeUtils.computeChainRatio(preObj.getAppliedAmount(), preObj.getAppliedAmount()));
            Double preIdRatio = BusinessComputeUtils.computeRatio(preObj.getRegisterIdentified(), preObj.getRegisterAmount());
            businessRegisterChainRatioVO.setIdentifiedRatioChainRatio(BusinessComputeUtils.computeChainRatio(preIdRatio, currIdRatio));
            Double preApplyPassRatio = BusinessComputeUtils.computeRatio(preObj.getApplyPass(), preObj.getAppliedAmount());
            businessRegisterChainRatioVO.setApplyPassRatioChainRatio(BusinessComputeUtils.computeChainRatio(preApplyPassRatio, currApplyPassRatio));
        }
        return businessRegisterChainRatioVO;
    }

    private BusinessRegisterChannelChainRatioVO copyChannelRegisterChainRatioVO(BusinessRegisterHour e, Map<String, BusinessRegisterHour> dataMap,
                                                                                Map<String, Long> totalChannel) {
        //获取前一个对象
        Long preIndex = Long.valueOf(e.getBizDate()) - 1;
        BusinessRegisterHour preObj = dataMap.get(preIndex.toString() + e.getChannel());

        //汇总结果
        BusinessRegisterChannelChainRatioVO businessRegisterChannelChainRatioVO = new BusinessRegisterChannelChainRatioVO();
        businessRegisterChannelChainRatioVO.setDate(e.getBizDate());
        businessRegisterChannelChainRatioVO.setChannel(e.getChannel());
        //计算在当前时间段，某渠道的注册率
        Double currChannelRatio = BusinessComputeUtils.computeRatio(e.getRegisterAmount(), totalChannel.get(e.getBizDate()));
        businessRegisterChannelChainRatioVO.setRatio(currChannelRatio);
        //计算环比
        if (null == preObj) {
            businessRegisterChannelChainRatioVO.setChainRatio(0.0);
        } else {
            Double preChannelRatio = BusinessComputeUtils.computeRatio(preObj.getRegisterAmount(), totalChannel.get(preObj.getBizDate()));
            businessRegisterChannelChainRatioVO.setChainRatio(BusinessComputeUtils.computeChainRatio(preChannelRatio, currChannelRatio));
        }
        return businessRegisterChannelChainRatioVO;
    }


//    @Override
//    public MessageTO getChannelCount(String type, Integer start, Integer end) {
//        MessageTO messageTO;
//        try{
//            HashMap<String,Integer> map = new HashMap<>();
//            map.put("start",start);
//            map.put("end",end);
//            messageTO = getChannelFunMapping().get(type).apply(map);
//        }catch (ServiceException se){
//            log.error("getChannelCount error!",se);
//            messageTO = ResponseUtils.failure("getChannelCount error!");
//        }
//        return messageTO;
//    }
//
//
//
//
//    public Map<String, Function<HashMap<String,Integer>,MessageTO>> getChannelFunMapping(){
//        Map<String,Function<HashMap<String,Integer>, MessageTO>> actionMappings = new HashMap<>();
//        actionMappings.put("hour",(map)-> {
//            List<BusinessApplyChannelCountHour> list =  businessApplyChannelCountHourDao
//                    .createQuery()
//                    .orderBy("end_time")
//                    .desc("end_time")
//                    .limit(map.get("start"),map.get("end"))
//                    .select();
//           return ResponseUtils.success(list);
//        });
//
//        actionMappings.put("day",(map)-> {
//            List<BusinessApplyChannelCountDay> list =  businessApplyChannelCountDayDao
//                    .createQuery()
//                    .orderBy("end_time")
//                    .desc("end_time")
//                    .limit(map.get("start"),map.get("end"))
//                    .select();
//            return ResponseUtils.success(list);
//        });
//
//        actionMappings.put("week",(map)-> {
//            List<BusinessApplyChannelCountWeek> list =  businessApplyChannelCountWeekDao
//                    .createQuery()
//                    .orderBy("end_time")
//                    .desc("end_time")
//                    .limit(map.get("start"),map.get("end"))
//                    .select();
//            return ResponseUtils.success(list);
//        });
//
//        actionMappings.put("month",(map)-> {
//            List<BusinessApplyChannelCountMonth> list =  businessApplyChannelCountMonthDao
//                    .createQuery()
//                    .orderBy("end_time")
//                    .desc("end_time")
//                    .limit(map.get("start"),map.get("end"))
//                    .select();
//            return ResponseUtils.success(list);
//        });
//
//        return actionMappings;
//    }
}
