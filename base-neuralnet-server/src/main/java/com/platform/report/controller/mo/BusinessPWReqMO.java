package com.platform.report.controller.mo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author wlhbdp
 * @create 2020/03/19 10:54 AM
 */
@Getter
@Setter
@ToString
public class BusinessPWReqMO {
    /**
     * eg: 02-社会人士，01-内部员工 注意：不传查所有渠道的数据
     */
    @ApiModelProperty(value = "客户类型")
    private String customerType;
    /**
     * eg: 20180910 必填
     */
    @ApiModelProperty(value = "查询开始时间", required = true)
    @NotBlank(message = "查询起始时间startDate不能为空")
    private String startDate;
    /**
     * eg: 20180911 必填
     */
    @ApiModelProperty(value = "查询结束时间", required = true)
    @NotBlank(message = "查询结束时间endDate不能为空")
    private String endDate;
    /**
     * , eg:线下门店商品分期支付 注意：必填字段
     */
    @ApiModelProperty(value = "消费类型")
    private String consumptionType;
}


