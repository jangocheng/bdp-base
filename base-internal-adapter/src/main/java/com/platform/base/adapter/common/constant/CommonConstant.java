package com.platform.base.adapter.common.constant;

public interface CommonConstant {
    /**
     * 判断是否有蜜蜂报告的标识
     */
    String MI_GUAN_HAS_REPORT = "hasReport";
    /**
     * 蜜蜂报告详情
     */
    String MI_GUAN_DETAIL = "detail";
    /**
     * tongdun缺失值填充
     */
    int TONG_DUN_MISSING_FILL_FRAUD_MODEL = 0;
    /**
     * 当hasReport为null或空值，特征值缺失填充此值
     */
    int MI_GUAN_MISSING_FILL_FRAUD_MODEL_1 = -999;
    /**
     * 当hasReport不为null或不为空值，特征值缺失填充此值
     */
    int MI_GUAN_MISSING_FILL_FRAUD_MODEL_2 = 0;


    /**
     * 反欺诈分模型名称
     */
    String FRAUD_SCORE_MODEL_NAME = "xgb_fraud_score_model.pkl";

    /**
     * 反欺诈分模型压缩方式
     */
    String FRAUD_SCORE_MODEL_COMPRESS_WAY = "joblib";
    /**
     * 反欺诈模型
     */
    String FRAUD_SCORE_MODEL_OUTPUT_NAME = "0,1";
    String FRAUD_SCORE_MODEL_OUTPUT_TYPE = "array";
}
