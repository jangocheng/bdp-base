package com.platform.base.adapter.service.impl.vo;

import lombok.Data;

/**
 * author: wlhbdp
 * create: 2020-06-22 16:01
 */
@Data
public class TongdunVO {

    /**
     * 同盾风险分(initial_final_score)
     */
    private Integer finalScore;

    /**
     * 1个月内申请人在多个平台申请借款(1m_apply_count)
     */
    private Integer oneMonthApplyLoan;

    /**
     * 3个月内申请人在多个平台申请借款(3m_apply_count)
     */
    private Integer threeMonthApplyLoan;

    /**
     * 6个月内申请人在多个平台申请借款
     */
    private Integer sixMonthApplyTimes;

}
