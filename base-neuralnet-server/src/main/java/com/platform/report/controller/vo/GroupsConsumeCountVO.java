package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/28 16:37
 */
@Getter
@Setter
public class GroupsConsumeCountVO {
    /**
     * 业务部门名称
     */
    private String groupId;
    /**
     *每日交易次数
     */
    private Long daily;
    /**
     *累积交易次数
     */
    private Long total;
}
