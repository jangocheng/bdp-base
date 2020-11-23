package com.platform.base.adapter.service;


import com.alibaba.fastjson.JSONObject;
import com.platform.base.adapter.web.controller.mo.PredictionReqMO;

/**
 * author: wlhbdp
 * create: 2020-06-21 18:00
 */
public interface ScoreService {

    JSONObject assembleDataForScoreModel(PredictionReqMO predictionReqMO) throws IllegalAccessException;

    Object getPrediction(JSONObject requestObj) ;
}
