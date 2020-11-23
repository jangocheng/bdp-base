package com.platform.finance.report.repo.ro;

import lombok.Data;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:03
 * @description 公共入参
 */
@Data
public class CommonRequestParamRO {

    /**
     * 资金方
     */
    private String fundingParty;

    /**
     * 资产渠道类型
     */
    private String channelType;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 产品类型
     */
    private String productType;

}
