package com.platform.base.adapter.web.controller.mo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * author: wlhbdp
 * create: 2020-06-22 17:32
 */
@Data
public class PredictionReqMO {
    /**
     * 身份证号码，不为null
     */
    @NotBlank(message = "identityCard必须不为空")
    private String identityCard;

    /**
     * 真实姓名，不为null
     */
    @NotBlank(message = "realName必须不为空")
    private String realName;

    /**
     * 手机号码，不为null
     */
    @NotBlank(message = "mobile必须不为空")
    private String mobile;

    /**
     * 年资
     */
//    private Double jobYears;

    /**
     * 学历
     */
//    private String degree;

    /**
     * 资位代码
     */
//    private String classCode;


    /**
     * 招聘来源,如政府
     */
    private String hireSourceRevised;

    /**
     * 厂区
     */
    private String facRevised;

    /**
     * 岗位(B)
     */
    private String gwRevised;

    //以下为同盾请求参数

    /**
     * 从网页端采集得到的设备指纹信息
     */
    private String tokenId;

    /**
     * 从移动客户端采集得到的设备指纹信息
     */
    private String blackBox;

    /**
     * 第一联系人姓名
     */
    private String contactOneName;

    /**
     * 第一联系人手机号
     */
    private String contactOneMobile;

    /**
     * 第二联系人姓名
     */
    private String contactTwoName;

    /**
     * 第二联系人手机号
     */
    private String contactTwoMobile;

    /**
     * 居住地址
     */
    private String livingAddress;
}
