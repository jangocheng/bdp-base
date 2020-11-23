package com.platform.base.adapter.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.base.adapter.common.util.ResponseUtils;
import com.platform.base.adapter.entity.MessageTO;
import com.platform.base.adapter.service.DingTalkRobotService;
import com.platform.base.adapter.service.ScoreService;
import com.platform.base.adapter.web.controller.mo.PredictionReqMO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评分卡预测
 * author: wlhbdp
 * create: 2020-06-21 16:01
 */
@RestController
@RequestMapping("/score")
@Slf4j
public class ScoreController {
    @Resource
    private ScoreService scoreService;
    @Resource
    private DingTalkRobotService dingTalkRobotService;

    @PostMapping("/predict")
    public MessageTO getPrediction(@RequestBody @Validated PredictionReqMO predictionReqMO) {
        //1.通过请求数据去查询第三方数据
        JSONObject ro;
        try {
            ro = scoreService.assembleDataForScoreModel(predictionReqMO);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            return ResponseUtils.responseFailure(e.getMessage());
        } catch (Exception e) {
            dingTalkRobotService.sendWarningMessage("请求三方数据失败！" + e.getMessage());
            log.error("请求三方数据失败!{}", e.getMessage());
            return ResponseUtils.responseFailure("请求三方数据失败! " + e.getMessage());
        }

        //2.调用预测接口
        try {
            log.info("请求预测接口：\n {}", JSON.toJSONString(ro, true));
            Object data = scoreService.getPrediction(ro);
            return ResponseUtils.responseSuccess(data);
        } catch (Exception e) {
            log.error("预测失败===>" + e.getMessage(), e);
            dingTalkRobotService.sendWarningMessage("预测失败===>" + e.getMessage());
            return ResponseUtils.responseFailure(e.getMessage());
        }
    }
}
