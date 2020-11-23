package com.platform.base.adapter.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.base.adapter.common.config.property.ThirdParty;
import com.platform.base.adapter.common.constant.ResponseCode;
import com.platform.base.adapter.common.constant.ThirdPartyUrl;
import com.platform.base.adapter.common.util.ModelROAssembleUtils;
import com.platform.base.adapter.service.ScoreService;
import com.platform.base.adapter.service.impl.bo.HrReqBO;
import com.platform.base.adapter.service.impl.bo.ResponseBO;
import com.platform.base.adapter.service.impl.bo.ResponseModelBO;
import com.platform.base.adapter.service.impl.ro.MiguanRO;
import com.platform.base.adapter.service.impl.ro.ModelRO;
import com.platform.base.adapter.web.controller.mo.PredictionReqMO;
import com.platform.base.adapter.service.impl.ro.TongdunRO;
import com.platform.base.adapter.service.impl.vo.HrVO;
import com.platform.base.adapter.service.impl.vo.MiguanVO;
import com.platform.base.adapter.service.impl.vo.TongdunVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * author: wlhbdp
 * create: 2020-06-21 18:31
 */
@Slf4j
@Service
public class ScoreServiceImpl implements ScoreService {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ThirdParty thirdParty;

    @Override
    public JSONObject assembleDataForScoreModel(PredictionReqMO predictionReqMO) throws IllegalAccessException {
        TongdunRO tongdunRO = new TongdunRO();
        MiguanRO miguanRO = new MiguanRO();

        BeanUtils.copyProperties(predictionReqMO, tongdunRO);
        BeanUtils.copyProperties(predictionReqMO, miguanRO);

        TongdunVO tongdunVO;
        try {
            tongdunVO = requestForTongdun(tongdunRO);
        } catch (Exception e) {
            log.error("请求同盾数据出错：" + tongdunRO.toString(), e);
            throw new RuntimeException("请求同盾数据出错：" + tongdunRO.toString(), e);
        }
        MiguanVO miguanVO;
        try {
            miguanVO = requestForMiguan(miguanRO);
        } catch (Exception e) {
            throw new RuntimeException("请求蜜罐数据出错：" + miguanRO.toString(), e);
        }
        HrVO hrVO;
        try {
            hrVO = requestForHr(predictionReqMO);
        } catch (Exception e) {
            throw new RuntimeException("请求人资数据出错：" + predictionReqMO.getIdentityCard(), e);
        }
        ModelRO modelRO = ModelROAssembleUtils.parseDataForModelRO(
                "xgboostTest", "mleap",
                "probability", "tensor",
                tongdunVO, miguanVO, hrVO);
        return JSONObject.parseObject(JSON.toJSONString(modelRO));
    }

    @Override
    public Object getPrediction(JSONObject requestObj) {
        String url = thirdParty.getModel() + ThirdPartyUrl.MODEL_PREDICT;
        ResponseModelBO modelMO = restTemplate.postForObject(url, requestObj, ResponseModelBO.class);
        if (modelMO != null && modelMO.getCode() == ResponseCode.REQUEST_SUCCESS.intValue()) {
            List rawData = (List) modelMO.getData();
            List data = (List)rawData.get(0);
            double score = ((double)data.get(0)) * 1000;
            return Math.round(score);
        } else {
            String msg = modelMO != null ? modelMO.getMessage() : "请求预测接口: " + url +"失败！";
            throw new RuntimeException(msg);
        }
    }


    private TongdunVO requestForTongdun(TongdunRO tongdunRO) {
        String url = thirdParty.getTongdun() + ThirdPartyUrl.TONG_DUN_URL;
        Object data = requestForData(tongdunRO, url);
        return JSON.parseObject(JSON.toJSONString(data), TongdunVO.class);
    }

    private MiguanVO requestForMiguan(MiguanRO miguanRO) {
        String url = thirdParty.getMiguan() + ThirdPartyUrl.MI_GUAN_URL;
        Object data = requestForData(miguanRO, url);
        return JSON.parseObject(JSON.toJSONString(data), MiguanVO.class);
    }

    private HrVO requestForHr(PredictionReqMO predictionReqMO) {
        String idCard = predictionReqMO.getIdentityCard();
        String url = thirdParty.getHr() + ThirdPartyUrl.HR_URL + idCard;
        log.info("request url: {}", url);
        HrReqBO hrMO = restTemplate.getForObject(url, HrReqBO.class);
        log.info("hr response: {}",hrMO);
        if (hrMO != null && hrMO.getSuccess()) {
            return convertHrVO(predictionReqMO, hrMO.getData());
        } else {
            return null;
        }
    }

    private HrVO convertHrVO(PredictionReqMO predictionReqMO, HrVO hrVO) {
        String hireSourceRevised = predictionReqMO.getHireSourceRevised();
        String facRevised = predictionReqMO.getFacRevised();
        String classCode = hrVO.getClassCode();
        String eduDegreeCode = hrVO.getEduDegreeCode();
        if (hireSourceRevised == null) {
            hireSourceRevised = "other";
        }
        if (facRevised == null) {
            facRevised = "other";
        }
        hrVO.setClassCode(classCode.substring(0, 8));
        hrVO.setEduDegreeCode(eduDegreeCode.substring(0, 9));
        hrVO.setFactoryCode(facRevised);
        hrVO.setHireSource(hireSourceRevised);
        return hrVO;
    }

    private Object requestForData(Object ro, String url) {
        log.info("request url: {}", url);
        ResponseBO responseBO = restTemplate.postForObject(url, ro, ResponseBO.class);
        log.info("response: {}", responseBO);
        if (responseBO != null && responseBO.getCode() == 0) {
            return responseBO.getData();
        } else {
            return "";
        }
    }

}
