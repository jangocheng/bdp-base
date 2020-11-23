package com.platform.base.adapter.web.controller;

import com.alibaba.fastjson.JSON;
import com.platform.base.adapter.common.util.ResponseUtils;
import com.platform.base.adapter.entity.MessageTO;
import com.platform.base.adapter.service.DingTalkRobotService;
import com.platform.base.adapter.service.ModelService;
import com.platform.base.adapter.service.ThirdPartService;
import com.platform.base.adapter.service.impl.bo.ModelPythonReqBO;
import com.platform.base.adapter.web.controller.mo.PredictionReqMO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 获取反欺诈分
 */
@RestController
@RequestMapping("/fraud")
@Slf4j
public class FraudScoreController {

    @Resource
    private ModelService modelService;

    @Resource
    private DingTalkRobotService dingTalkRobotService;

    @Resource
    private ThirdPartService thirdPartService;

    @PostMapping("/predict")
    public MessageTO predict(@RequestBody @Validated PredictionReqMO predictionReqMO) {
        //1.请求三方数据
        Long start = System.currentTimeMillis();
        ModelPythonReqBO ro;
        try {
            ro = thirdPartService.assembleDataForFraudModel(predictionReqMO);
        } catch (Exception e) {
            dingTalkRobotService.sendWarningMessage("收集模型参数失败,error message: " + e.getMessage());
            log.error("收集模型参数失败，error message:{}", e.getMessage());
            return ResponseUtils.responseFailure("收集模型参数失败，error message: " + e.getMessage());
        }

        //2.调用预测接口
        try {
            log.info("请求预测接口：\n {}", JSON.toJSONString(ro, true));
            Object data = modelService.predictForFraudScore(ro);
            Long end = System.currentTimeMillis();
            log.info("预测结果：{},总耗时：{}", data, (end - start));
            return ResponseUtils.responseSuccess(data);
        } catch (Exception e) {
            log.error("预测失败===>" + e.getMessage(), e);
            dingTalkRobotService.sendWarningMessage("预测失败===>" + e.getMessage());
            return ResponseUtils.responseFailure(e.getMessage());
        }
    }


}
