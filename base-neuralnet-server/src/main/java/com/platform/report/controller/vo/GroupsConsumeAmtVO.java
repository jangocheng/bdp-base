package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupsConsumeAmtVO {
    /**
     * 业务部门名称
     */
    private String groupId;
    /**
     *每日交易金额
     */
    private Double daily;
    /**
     *累积交易金额
     */
    private Double total;
}
