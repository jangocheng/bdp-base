package com.platform.report.controller.mo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author wlhbdp
 * @create 2020/03/21 9:17 AM
 */
@Getter
@Setter
@ToString
public class BusinessPopularizeChannelReqMO {
    /**
     * eg: aikoudai  注意：不传查所有渠道的数据
     */
    @ApiModelProperty(value = "查询渠道")
    private String channel;
    /**
     * eg: 20180910  必填
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
     * 注意：必填字段
     */
    @ApiModelProperty(value = "查询汇总字段", required = true)
    @NotBlank(message = "查询汇总字段不能为空，且必须为[gender,age,province,customerLevel,loanPeriod,educationalBackground]其中之一")
    private String summaryField;
}
