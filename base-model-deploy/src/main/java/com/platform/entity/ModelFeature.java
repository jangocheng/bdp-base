package com.platform.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Blob;

/**
 * author: wlhbdp
 * create: 2020-05-17 17:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModelFeature extends BaseDO {

    private String modelName;

    private String modelVersion;

    private String path;
}
