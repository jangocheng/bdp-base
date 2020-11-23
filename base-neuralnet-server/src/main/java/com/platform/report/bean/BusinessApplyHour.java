package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;


@Getter
@Setter
@Table(name = "base-bi.business_apply_hour")
public class BusinessApplyHour {
    private String id;
    private Long appliedAmount;
    private Long applyCancel;
    private Long applyIng;
    private Long applyPass;
    private Long applyRefuse;
    private Long applyReturn;
    private Double approvalSum;
    private String bizDate;
    private String channel;
    private String customerType;
    private String customerTypeName;
    private Long machinePass;
    private Long machineRefuse;
    private Long serviceValid;
    private String target;
    private String targetName;
}
