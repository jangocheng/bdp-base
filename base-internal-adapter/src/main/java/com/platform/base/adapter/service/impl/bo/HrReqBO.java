package com.platform.base.adapter.service.impl.bo;

import com.platform.base.adapter.service.impl.vo.HrVO;
import lombok.Data;

/**
 * author: wlhbdp
 * create: 2020-06-24 14:40
 */
@Data
public class HrReqBO {
    private Integer code;

    private HrVO data;

    private String msg;

    private Boolean success;
}
