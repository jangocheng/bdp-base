package com.platform.base.adapter.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.base.adapter.common.config.property.ThirdParty;
import com.platform.base.adapter.common.constant.CommonConstant;
import com.platform.base.adapter.common.constant.ResponseCode;
import com.platform.base.adapter.common.constant.ThirdPartyUrl;
import com.platform.base.adapter.common.util.ModelParamUtils;
import com.platform.base.adapter.service.ThirdPartService;
import com.platform.base.adapter.service.impl.bo.FengKongResBO;
import com.platform.base.adapter.service.impl.bo.ModelPythonReqBO;
import com.platform.base.adapter.service.impl.ro.ThreeFactoryRO;
import com.platform.base.adapter.service.impl.vo.FraudScoreModelVO;
import com.platform.base.adapter.web.controller.mo.PredictionReqMO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@Service
public class ThirdPartServiceImpl implements ThirdPartService {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ThirdParty thirdParty;

    @Override
    public ModelPythonReqBO assembleDataForFraudModel(PredictionReqMO predictionReqMO) {

        ThreeFactoryRO threeFactoryRO = new ThreeFactoryRO();
        BeanUtils.copyProperties(predictionReqMO, threeFactoryRO);

        //获取三方数据，三方数据的缺失值分开处理：适用于每个三方的缺失值处理方式不一致
        //获取tongdun数据
        String tongdunUrl = thirdParty.getTongdun() + ThirdPartyUrl.TONG_DUN_FOR_FRAUD_SCORE_MODEL_API;
        JSONObject tongDunData = requestForData(threeFactoryRO, tongdunUrl);
        ModelParamUtils.dealMissingValue(tongDunData, CommonConstant.TONG_DUN_MISSING_FILL_FRAUD_MODEL);

        //获取蜜罐数据
        String miguanUrl = thirdParty.getMiguan() + ThirdPartyUrl.MI_GUAN_FOR_FRAUD_SCORE_MODEL_API;
        JSONObject miguanData = requestForData(threeFactoryRO, miguanUrl);
        JSONObject miGuanDetail = dealMiguanResultMissingForFraudModel(miguanData);

        //模型参数汇总
        return collectFraudModelParams(tongDunData, miGuanDetail);

    }

    private ModelPythonReqBO collectFraudModelParams(JSONObject tongDunData, JSONObject miGuanDetail) {
        if (!ModelParamUtils.checkModelParams(FraudScoreModelVO.class, tongDunData, miGuanDetail)) {
            throw new RuntimeException("三方返回结果字段不足，tongdun:" + tongDunData + ",miguan detail:" + miGuanDetail);
        }

        ModelPythonReqBO modelRO = new ModelPythonReqBO();
        modelRO.setModelName(CommonConstant.FRAUD_SCORE_MODEL_NAME);
        modelRO.setModelCompressWay(CommonConstant.FRAUD_SCORE_MODEL_COMPRESS_WAY);
        modelRO.setOutputType(CommonConstant.FRAUD_SCORE_MODEL_OUTPUT_TYPE);
        modelRO.setOutputName(CommonConstant.FRAUD_SCORE_MODEL_OUTPUT_NAME);
        JSONObject data = new JSONObject();
        data.putAll(tongDunData);
        data.putAll(miGuanDetail);
        FraudScoreModelVO modelVO = data.toJavaObject(FraudScoreModelVO.class);
        modelRO.setData(modelVO);

        return modelRO;
    }

    private JSONObject dealMiguanResultMissingForFraudModel(JSONObject miguanData) {
        String miGuanHasReport = null;
        JSONObject miGuanDetail = null;
        if (null != miguanData) {
            miGuanHasReport = miguanData.getString(CommonConstant.MI_GUAN_HAS_REPORT);
            miGuanDetail = miguanData.getJSONObject(CommonConstant.MI_GUAN_DETAIL);
        }

        if (StringUtils.isNotBlank(miGuanHasReport)) {
            ModelParamUtils.dealMissingValue(miGuanDetail, CommonConstant.MI_GUAN_MISSING_FILL_FRAUD_MODEL_2);
        } else {
            ModelParamUtils.dealMissingValue(miGuanDetail, CommonConstant.MI_GUAN_MISSING_FILL_FRAUD_MODEL_1);
        }

        return miGuanDetail;
    }


    private JSONObject requestForData(Object ro, String url) {
        FengKongResBO responseBO;
        try {
            responseBO = restTemplate.postForObject(url, ro, FengKongResBO.class);
        } catch (Exception e) {
            throw new RuntimeException("请求三方数据失败！，url:" + url + ",request param:" + JSON.toJSONString(ro) + ",reason:" + e.getMessage());
        }

        if (responseBO == null || !ResponseCode.CODE_SUCCESS.equals(responseBO.getCode())) {
            throw new RuntimeException("请求三方数据失败！，url:" + url + ",request param:" + JSON.toJSONString(ro) + ",response:" + JSON.toJSONString(responseBO));
        }

        if (null == responseBO.getData()) {
            throw new RuntimeException("三方返回数据无效，url:" + url + ",request param:" + JSON.toJSONString(ro) + ",response:" + JSON.toJSONString(responseBO));
        }
        log.info("请求三方 url:{}，返回结果：{}", url, responseBO);

        return responseBO.getData();

    }
}
