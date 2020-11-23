package com.platform.base.adapter.service.impl;

import com.alibaba.fastjson.JSON;
import com.platform.base.adapter.common.config.property.ThirdParty;
import com.platform.base.adapter.common.constant.ResponseCode;
import com.platform.base.adapter.common.constant.ThirdPartyUrl;
import com.platform.base.adapter.service.ModelService;
import com.platform.base.adapter.service.impl.bo.ModelPythonReqBO;
import com.platform.base.adapter.service.impl.bo.ResponseModelBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@Service
public class ModelServiceImpl implements ModelService {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ThirdParty thirdParty;

    @Override
    public Object predictForFraudScore(ModelPythonReqBO modelReqRO) {
        String url = thirdParty.getPythonModelBaseUrl() + ThirdPartyUrl.FRAUD_SCORE_MODEL;
        String data = restTemplate.postForObject(url, modelReqRO, String.class);
        ResponseModelBO modelBO = JSON.parseObject(data, ResponseModelBO.class);
        if (modelBO != null && modelBO.getCode().equals(ResponseCode.REQUEST_SUCCESS)) {
            return modelBO.getData();
        } else {
            String msg = modelBO != null ? modelBO.getMessage() : "请求预测接口: " + url + "失败！";
            throw new RuntimeException(msg);
        }
    }
}
