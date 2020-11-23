package com.platform.base.adapter.service.impl.vo;

import lombok.Data;

/**
 * author: wlhbdp
 * create: 2020-06-24 14:05
 */
@Data
public class HrVO {
    /**
     * 年资
     */
    private Double seniority;

    /**
     * 学历
     */
    private String eduDegreeCode;

    /**
     * 资位代码
     */
    private String classCode;

    /**
     * 岗位(B)，衍生字段
     */
    private String empStatus;

    /**
     * 招聘来源,如政府
     */
    private String hireSource;

    /**
     * 厂区
     */
    private String factoryCode;
}
