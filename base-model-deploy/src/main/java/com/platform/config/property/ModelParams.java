package com.platform.config.property;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author: wlhbdp
 * create: 2020-05-21 14:08
 */
@Data
public class ModelParams {

    private String cacheBuilderSpec;

    private Boolean intern;

    private Boolean optimize;

    private Boolean measure;

    private Integer loop;

    /**
     * Exclude non-final output fields
     */
    private Boolean filterOutput;

    /**
     * Permit missing input field columns
     */
    private Boolean sparse;

    /**
     * padding missing value with strings
     */
    private List<String> missingValues = new ArrayList<>(
            Collections.singletonList("NA"));
}
