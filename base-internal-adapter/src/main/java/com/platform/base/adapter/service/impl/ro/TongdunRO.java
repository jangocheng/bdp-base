package com.platform.base.adapter.service.impl.ro;

import lombok.Data;

/**
 * author: wlhbdp
 * create: 2020-06-22 16:29
 */
@Data
public class TongdunRO {

    private String blackBox;

    /**
     * 第一联系人手机号
     */
    private String contactOneMobile;

    /**
     * 第一联系人姓名
     */
    private String contactOneName;

    /**
     * 第二联系人手机号
     */
    private String contactTwoMobile;

    /**
     * 第二联系人姓名
     */
    private String contactTwoName;

    /**
     * 身份证
     */
    private String identityCard;

    /**
     * 居住地址
     */
    private String livingAddress;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 姓名
     */
    private String realName;

    /**
     *
     */
    private String seqId;

    /**
     *
     */
    private String tokenId;
}
