package com.platform.report.controller;

import com.platform.report.service.DailySalaryService;
import com.platform.spring.bean.MessageTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 实时新资接口
 * @author wlhbdp
 * @create 2020/03/28 15:56
 */
@RestController
@RequestMapping("/dailySalary")
public class DailySalaryController {
    @Resource
    private DailySalaryService dailySalaryService;

    @ApiOperation(value="实时交易地图数据查询")
    @GetMapping("/realtimeConsumeData")
    public MessageTO getRealTimeConsumeData() {
        return dailySalaryService.getRTConsumeDataList();
    }

    @ApiOperation(value="推送今日/累计用户数")
    @GetMapping("/syncData")
    public MessageTO getSyncData() {
        return dailySalaryService.getSyncGroupsDataList();
    }

    @ApiOperation(value="今日/累计 交易金额")
    @GetMapping("/consumeAmountData")
    public MessageTO getConsumeAmtData() {
        return dailySalaryService.getGroupsConsumeAmtList();
    }

    @ApiOperation(value="今日/累计交易次数")
    @GetMapping("/consumeCountData")
    public MessageTO getGroupsConsumeCount() {
        return dailySalaryService.getGroupsConsumeCtnList();
    }

    @ApiOperation(value="汇总数据查询")
    @GetMapping("/summaryData")
    public MessageTO getSummaryData() {
        return dailySalaryService.getGroupsSummaryData();
    }

    @ApiOperation(value="各次业务部门完成率")
    @GetMapping("/completeData")
    public MessageTO getPercentageCompleteData() {
        return dailySalaryService.getGroupsPntCompleteData();
    }


}
