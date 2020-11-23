package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wlhbdp
 * @create 2020/03/28 16:17
 */
@Getter
@Setter
public class SyncGroupsDataVO {
    /**
     * 业务部门名称
     */
    private String groupId;
    /**
     *每日用户数量
     */
    private Long daily;
    /**
     *累积用户数量
     */
    private Long total;
}
