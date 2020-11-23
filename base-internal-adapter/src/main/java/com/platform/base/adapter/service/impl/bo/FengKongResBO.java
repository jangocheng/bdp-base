package com.platform.base.adapter.service.impl.bo;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FengKongResBO {

    private Integer code;

    private JSONObject data;

    private String msg;

    private String debugInfo;
}
