package com.platform.entity;

import lombok.Data;

import java.util.Date;

/**
 * author: wlhbdp
 * create: 2020-05-17 17:21
 */
@Data
public class BaseDO {

    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private Integer version;
}
