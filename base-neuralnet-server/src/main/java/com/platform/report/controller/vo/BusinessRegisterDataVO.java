package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BusinessRegisterDataVO {
    /**
     * 申请各环节数量及环比
     */
    private List<BusinessRegisterChainRatioVO> registerChainRatios;
    /**
     * 渠道申请量占比及数量环比
     */
    private List<BusinessRegisterChannelChainRatioVO> channelRegisterChainRatios;
    /**
     * 渠道申请报表
     */
    private List<BusinessRegisterReportVO> reportVOList;
}
