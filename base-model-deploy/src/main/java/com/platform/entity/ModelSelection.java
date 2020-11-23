package com.platform.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * author: wlhbdp
 * create: 2020-05-17 17:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModelSelection extends BaseDO {

    private Integer modelId;

    private String modelName;
}
