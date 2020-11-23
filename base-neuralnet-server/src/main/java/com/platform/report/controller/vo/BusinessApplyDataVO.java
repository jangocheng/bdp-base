package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wlhbdp
 * @create 2020/03/26 18:41
 */
@Getter
@Setter
public class BusinessApplyDataVO {

    /**
     * 申请各环节数量及环比
     */
    private List<BusinessApplyChainRatioVO> applyChainRatios;
    /**
     * 渠道申请量占比及数量环比
     */
    private List<BusinessApplyReportVO> report;
    /**
     * 渠道申请报表
     */
    private List<BusinessApplyChannelChainRatioVO> channelApplyChainRatios;
}
