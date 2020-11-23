package com.platform.base.adapter.service.impl.vo;

import lombok.Data;

/**
 * author: wlhbdp
 * create: 2020-06-22 15:39
 */
@Data
public class MiguanVO {
    /**
     * 灰度分(initial_phone_gray_score)
     */
    private Integer grayScore;

    /**
     * 直接联系人在黑名单中的数量(contacts_class1_blacklist_cnt)
     */
    private Integer directContactBlacklistNum;

    /**
     * 引起二阶黑名单人数(contacts_class2_blacklist_cnt)
     */
    private Integer indirectContactBlacklistNum;

    /**
     * 一阶联系人总数(contacts_class1_cnt)
     */
    private Integer directContactTotalNum;

    /**
     * 引起占比=引起二阶黑名单人数/一阶联系人总数(contacts_router_ratio)
     */
    private Double contactsRouterRatio;

    /**
     * 间接联系人在黑名单的数量(contacts_router_cnt)
     */
    private Integer secondContactBlacklistNum;

    /**
     * 被机构查询次数(searched_org_cnt)
     */
    private Integer searchedOrgNum;

    /**
     * 信用卡代偿(card_pay)
     */
    private Boolean cardPay;
}
