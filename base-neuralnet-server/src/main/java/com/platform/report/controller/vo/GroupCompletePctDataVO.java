package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GroupCompletePctDataVO {

    /**
     * 业务部门名称
     */
    private String groupId;
    /**
     * 有效激活人数
     */
    private Long activeAmt;
    /**
     * 有效推送数
     */
    private Long syncAmt;
}
