package com.platform.report.controller;

import com.platform.report.service.FbdDataService;
import com.platform.spring.bean.MessageTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 电子钱包核心数据接口
 * @author wlhbdp
 * @create 2020/03/26 15:56
 */
@RestController
@RequestMapping("/rtbi/elecwallet")
public class FbdDataController {
    @Resource
    private FbdDataService fbdDataService;

    @ApiOperation(value="每日实时消费金额")
    @GetMapping("/dailyRealTimeConsumption")
    public MessageTO getDailyRealTimeConsumption() {
        return fbdDataService.getDailyRealTimeConsumption();
    }

}
