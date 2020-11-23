package com.platform.finance.report.web.mo;

import com.platform.finance.report.common.mo.PageableMO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wlhbdp
 * @date 2020/9/2 17:41
 * @description 公共入参
 */
@Data
public class CommonRequestParamMO extends PageableMO {

    /**
     * 资金方
     */
    @ApiModelProperty("资金方")
    private String fundingParty;

    /**
     * 资产渠道类型
     */
    @ApiModelProperty("资产渠道类型")
    private String channelType;

    /**
     * 渠道名称
     */
    @ApiModelProperty("渠道名称")
    private String channelName;

    /**
     * 产品类型
     */
    @ApiModelProperty("产品类型")
    private String productType;
}
