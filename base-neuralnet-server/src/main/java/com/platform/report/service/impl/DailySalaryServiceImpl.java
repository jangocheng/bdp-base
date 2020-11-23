package com.platform.report.service.impl;

import com.platform.report.bean.*;
import com.platform.report.common.BiCommon;
import com.platform.report.common.utils.DateUtils;
import com.platform.report.common.utils.DigitUtils;
import com.platform.report.controller.vo.*;
import com.platform.report.dao.*;
import com.platform.report.service.DailySalaryService;
import com.platform.spring.bean.MessageTO;
import com.platform.spring.exception.ServiceException;
import com.platform.spring.util.ResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wlhbdp
 * @create 2020/03/28 16:03
 */
@Service
@Log4j2
public class DailySalaryServiceImpl implements DailySalaryService {
    @Resource
    private SalaryBCountDao salaryBCountDao;

    @Resource
    private SalaryFiiCtCountDao salaryFiiCtCountDao;

    @Resource
    private SalaryFiiCesgbCountDao salaryFiiCesgbCountDao;

    @Resource
    private SalaryIdpbgCountDao salaryIdpbgCountDao;

    @Resource
    private SalaryIpebgCountDao salaryIpebgCountDao;

    @Resource
    private SalaryDCountDao salaryDCountDao;

    @Resource
    private DailySalaryDailyConsumeAmtDao dailySalaryDailyConsumeAmtDao;

    @Resource
    private DailySalaryTotalConsumeAmtDao dailySalaryTotalConsumeAmtDao;

    @Resource
    private DailySalaryDailyConsumeCntDao dailySalaryDailyConsumeCntDao;

    @Resource
    private DailySalaryTotalConsumeCntDao dailySalaryTotalConsumeCntDao;

    @Resource
    private DailySalaryActivateDao dailySalaryActivateDao;

    @Resource
    private DailySalaryFbdApplyDao dailySalaryFbdApplyDao;

    @Resource
    private DailySalaryGroupCompletePctDao dailySalaryGroupCompletePctDao;

    @Override
    public MessageTO getRTConsumeDataList() {
        List<RTConsumeDataVO> rtConsumeDataVOS = new ArrayList<>();
        RTConsumeDataVO rt = new RTConsumeDataVO();
        rt.setUsageType("0202");
        rt.setLatitude(34.23);
        rt.setLongitude(43.34);
        rtConsumeDataVOS.add(rt);
        return ResponseUtil.genSuccessResult(rtConsumeDataVOS);
    }

    @Override
    public MessageTO getSyncGroupsDataList() {
        MessageTO messageTO = ResponseUtil.genSuccessResult();
        try {
            Map<String, Long> dayMap = getSalaryMap(BiCommon.SALARY_DAY);
            Map<String, Long> totalMap = getSalaryMap(BiCommon.SALARY_TOTAL);
            List<SyncGroupsDataVO> rtList = new ArrayList<>();
            SyncGroupsDataVO rt;
            for (String key : dayMap.keySet()) {
                rt = new SyncGroupsDataVO();
                rt.setGroupId(key);
                rt.setDaily(dayMap.get(key));
                rt.setTotal(totalMap.get(key));
                rtList.add(rt);
            }
            messageTO.setRows(rtList);
        } catch (ServiceException se) {
            messageTO = ResponseUtil.genFailMessage("getSyncGroupsDataList error！");
            log.error("getSyncGroupsDataList error！", se);
        }
        return messageTO;
    }

    @Override
    public MessageTO getGroupsConsumeAmtList() {

        MessageTO messageTO = ResponseUtil.genSuccessResult();
        try {


            String bizDate = DateUtils.getBizDate(System.currentTimeMillis());
            //获取最新的数据的id集合
            List<DailySalaryDailyConsumeAmt> dailyList = dailySalaryDailyConsumeAmtDao.selectByBizDate(bizDate);
            List<DailySalaryTotalConsumeAmt> totalList = dailySalaryTotalConsumeAmtDao.selectByBizDate(bizDate);

            //数据处理
            Map<String, Double> dailyMap = dailyList
                    .stream()
                    .filter(e -> StringUtils.isNotBlank(e.getSjtname()))
                    .collect(Collectors.toMap(DailySalaryDailyConsumeAmt::getSjtname, DailySalaryDailyConsumeAmt::getDailyConsumeAmt));

            //获取结果数据
            List<GroupsConsumeAmtVO> consumeAmtList = new ArrayList<>();
            for (DailySalaryTotalConsumeAmt totalAmt : totalList) {
                if (StringUtils.isBlank(totalAmt.getSjtname())) {
                    continue;
                }
                GroupsConsumeAmtVO consumeAmt = new GroupsConsumeAmtVO();
                consumeAmt.setTotal(DigitUtils.getValidValue(totalAmt.getTotalConsumeAmt()));
                consumeAmt.setDaily(dailyMap.get(totalAmt.getSjtname()));
                consumeAmt.setGroupId(totalAmt.getSjtname());
                consumeAmtList.add(consumeAmt);
            }
            messageTO.setRows(consumeAmtList);
        } catch (ServiceException se) {
            messageTO = ResponseUtil.genFailMessage("今日/累计 交易金额查询失败");
            log.error("今日/累计 交易金额查询失败", se);
        }
        return messageTO;
    }


    @Override
    public MessageTO getGroupsConsumeCtnList() {
        MessageTO messageTO = ResponseUtil.genSuccessResult();
        try {
            //从数据库查询相关数据
            String bizDate = DateUtils.getBizDate(System.currentTimeMillis());
            List<DailySalaryDailyConsumeCnt> dailyList = dailySalaryDailyConsumeCntDao.selectByBizDate(bizDate);
            List<DailySalaryTotalConsumeCnt> totalList = dailySalaryTotalConsumeCntDao.selectByBizDate(bizDate);

            //数据处理
            Map<String, Long> dailyMap = dailyList.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getSjtname()))
                    .collect(Collectors.toMap(DailySalaryDailyConsumeCnt::getSjtname, DailySalaryDailyConsumeCnt::getDailyConsumeCnt));

            //获取结果数据
            List<GroupsConsumeCountVO> consumeCtnList = new ArrayList<>();
            for (DailySalaryTotalConsumeCnt totalCtn : totalList) {
                if (StringUtils.isBlank(totalCtn.getSjtname())) {
                    continue;
                }
                GroupsConsumeCountVO consumeCtn = new GroupsConsumeCountVO();
                consumeCtn.setTotal(DigitUtils.getValidValue(totalCtn.getTotalConsumeCnt()));
                consumeCtn.setDaily(dailyMap.get(totalCtn.getSjtname()));
                consumeCtn.setGroupId(totalCtn.getSjtname());
                consumeCtnList.add(consumeCtn);
            }
            messageTO.setRows(consumeCtnList);
        } catch (ServiceException se) {
            messageTO = ResponseUtil.genFailMessage("今日/累计 交易次数查询失败");
            log.error("今日/累计 交易次数查询失败", se);
        }
        return messageTO;
    }


    @Override
    public MessageTO getGroupsSummaryData() {
        try {
            GroupsSummaryDataVO groupsSummaryDataVO = new GroupsSummaryDataVO();
            String bizDate = DateUtils.getBizDate(System.currentTimeMillis());

            //获取累计推送用户数
            Map<String, Long> pushUsers = getSalaryMap(BiCommon.SALARY_TOTAL);
            Long totalCount = pushUsers.entrySet().stream().mapToLong(Map.Entry::getValue).sum();
            groupsSummaryDataVO.setTotalCount(totalCount);

            //获取累计注册的用户数量和累计激活新资额度的用户数量
            List<DailySalaryActivate> activateList = dailySalaryActivateDao.createQuery()
                    .andEq("biz_date", bizDate).desc("id")
                    .limit(1, 1).select();
            if (activateList.isEmpty()) {
                groupsSummaryDataVO.setTotalRegister(0L);
                groupsSummaryDataVO.setTotalActive(0L);
            } else {
                DailySalaryActivate activate = activateList.get(0);
                groupsSummaryDataVO.setTotalRegister(DigitUtils.getValidValue(activate.getTotalRegisterAmount()));
                groupsSummaryDataVO.setTotalActive(DigitUtils.getValidValue(activate.getActivateSalaryAmount()));

            }
            //获取电子钱包申请用户数量和申请通过的用户数量
            List<DailySalaryFbdApply> applyList = dailySalaryFbdApplyDao.createQuery()
                    .andEq("biz_date", bizDate).desc("id")
                    .limit(1, 1).select();
            if (applyList.isEmpty()) {
                groupsSummaryDataVO.setTotalCreditRegister(0L);
                groupsSummaryDataVO.setTotalApproved(0L);
            } else {
                DailySalaryFbdApply activate = applyList.get(0);
                groupsSummaryDataVO.setTotalCreditRegister(DigitUtils.getValidValue(activate.getFbdRegisterAmount()));
                groupsSummaryDataVO.setTotalApproved(DigitUtils.getValidValue(activate.getFbdApprovedAmount()));
            }

            //累计交易次数
            List<DailySalaryTotalConsumeCnt> totalCtnList = dailySalaryTotalConsumeCntDao.selectByBizDate(bizDate);
            Long totalConsumeCtn = totalCtnList.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getSjtname()))
                    .mapToLong(DailySalaryTotalConsumeCnt::getTotalConsumeCnt).sum();
            groupsSummaryDataVO.setTotalConsumeCount(totalConsumeCtn);

            //累计交易金额
            List<DailySalaryTotalConsumeAmt> totalAmtList = dailySalaryTotalConsumeAmtDao.selectByBizDate(bizDate);
            Double totalConsumeAmt = totalAmtList.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getSjtname()))
                    .mapToDouble(DailySalaryTotalConsumeAmt::getTotalConsumeAmt).sum();
            groupsSummaryDataVO.setTotalConsumeAmount(totalConsumeAmt);

            return ResponseUtil.genSuccessResult(groupsSummaryDataVO);
        } catch (ServiceException se) {
            log.error("今日/累计 交易次数查询失败", se);
            return ResponseUtil.genFailMessage("今日/累计 交易次数查询失败");

        }
    }

    @Override
    public MessageTO getGroupsPntCompleteData() {
        MessageTO messageTO = ResponseUtil.genSuccessResult();
        try {
            //获取各个业务部门的有效推送人数
            Map<String, Long> synAmtGroups = getSalaryMap(BiCommon.SALARY_TOTAL);
            //获取各个业务部门有效的激活人数
            String bizDate = DateUtils.getBizDate(System.currentTimeMillis());
            List<DailySalaryGroupCompletePct> completePctList = dailySalaryGroupCompletePctDao.selectByBizDate(bizDate);
            Map<String, Long> pctMap = completePctList.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getSjtname()))
                    .collect(Collectors.toMap(DailySalaryGroupCompletePct::getSjtname, DailySalaryGroupCompletePct::getActiveAmt));

            //汇总结果数据
            List<GroupCompletePctDataVO> result = new ArrayList<>();
            for (Map.Entry<String, Long> entry : synAmtGroups.entrySet()) {
                //判断分母的有效性
                if (!DigitUtils.validDenominator(entry.getValue())) {
                    continue;
                }

                //判断分数的有效性
                Long member = DigitUtils.getValidValue(pctMap.get(entry.getKey()));
                if (member > entry.getValue()) {
                    continue;
                }

                //保存整理的数据
                GroupCompletePctDataVO data = new GroupCompletePctDataVO();
                data.setSyncAmt(entry.getValue());
                data.setGroupId(entry.getKey());
                data.setActiveAmt(member);

                result.add(data);
            }
            messageTO.setRows(result);
        } catch (ServiceException se) {
            messageTO = ResponseUtil.genFailMessage("今日/累计 交易金额查询失败");
            log.error("今日/累计 交易金额查询失败", se);
        }
        return messageTO;
    }



    /**
     * 获取薪资用户数据
     *
     * @param type
     * @return
     * @throws ServiceException
     */
    public Map<String, Long> getSalaryMap(String type) throws ServiceException {
        Map<String, Long> result = new HashMap<>();
        Long fiiCount = 0L;
        Long caaCount = 0L;
        Long bCount = 0L;
        Long dCount = 0L;
        Long aCount = 0L;
        List<SalaryBCount> salaryBDayList = salaryBCountDao.createQuery().andEq("type", type).desc("id").limit(1, 1).select();
        List<SalaryFiiCesgbCount> salaryFiiCesgbDayList = salaryFiiCesgbCountDao.createQuery().andEq("type", type).desc("id").limit(1, 1).select();
        List<SalaryFiiCtCount> salaryFiiCtDayList = salaryFiiCtCountDao.createQuery().andEq("type", type).desc("id").limit(1, 1).select();
        List<SalaryIdpbgCount> salaryIdpbgDayList = salaryIdpbgCountDao.createQuery().andEq("type", type).desc("id").limit(1, 1).select();
        List<SalaryIpebgCount> salaryIpebgDayList = salaryIpebgCountDao.createQuery().andEq("type", type).desc("id").limit(1, 1).select();
        List<SalaryDCount> salaryDDayList = salaryDCountDao.createQuery().andEq("type", type).desc("id").limit(1, 1).select();

        if (salaryBDayList != null && !salaryBDayList.isEmpty()) {
            bCount = DigitUtils.getValidValue(salaryBDayList.get(0).getCount());
        }
        if (salaryFiiCesgbDayList != null && !salaryFiiCesgbDayList.isEmpty()) {
            fiiCount += DigitUtils.getValidValue(salaryFiiCesgbDayList.get(0).getCount());
        }
        if (salaryFiiCtDayList != null && !salaryFiiCtDayList.isEmpty()) {
            fiiCount += DigitUtils.getValidValue(salaryFiiCtDayList.get(0).getCount());
        }

        if (salaryIdpbgDayList != null && !salaryIdpbgDayList.isEmpty()) {
            caaCount = DigitUtils.getValidValue(salaryIdpbgDayList.get(0).getCount());
        }
        if (salaryIpebgDayList != null && !salaryIpebgDayList.isEmpty()) {
            aCount = DigitUtils.getValidValue(salaryIpebgDayList.get(0).getCount());
        }
        if (salaryDDayList != null && !salaryDDayList.isEmpty()) {
            dCount = DigitUtils.getValidValue(salaryDDayList.get(0).getCount());
        }
        result.put("FII", fiiCount);
        result.put("CAA", caaCount);
        result.put("D", dCount);
        result.put("B", bCount);
        result.put("A", aCount);
        return result;
    }


}
