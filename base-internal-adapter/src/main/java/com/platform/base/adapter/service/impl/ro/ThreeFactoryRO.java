package com.platform.base.adapter.service.impl.ro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreeFactoryRO {
    /**
     * 身份证号码，不为null
     */
    private String identityCard;

    /**
     * 真实姓名，不为null
     */
    private String realName;

    /**
     * 手机号码，不为null
     */
    private String mobile;
}
