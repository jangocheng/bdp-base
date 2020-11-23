package com.platform.finance.report.repo.ro;

import lombok.Data;

/**
 * @author wlhbdp
 * @date 2020/9/2 11:41
 * @description 逾期表
 */
@Data
public class OverdueReportRO extends CommonRequestParamRO {

    /**
     * 产品子类型
     */
    private String productChildType;

    /**
     * 应还年月开始
     */
    private String payDateStart;

    /**
     * 应还年月结束
     */
    private String payDateEnd;

    /**
     * 十四级分类
     */
    private String overdueGrade;
}
