package com.platform.base.adapter.service.impl.ro;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 请求模型预测接口所用数据格式
 * author: wlhbdp
 * create: 2020-06-24 16:24
 */
@Data
public class ModelRO {
    private NameValuePair model;

    private FeatureSchema schema;

    private NameValuePair output;

    private List<List<Object>> rows;

    @Data
    @AllArgsConstructor
    public static class NameValuePair {
        private String name;

        private String type;
    }

    @Data
    @AllArgsConstructor
    public static class FeatureSchema {

        private List<NameValuePair> fields;
    }
}
