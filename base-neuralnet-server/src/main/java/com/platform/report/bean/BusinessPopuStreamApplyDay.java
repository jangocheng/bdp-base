package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;


@Getter
@Setter
@Table(name = "base-bi.business_popu_stream_apply_day")
public class BusinessPopuStreamApplyDay {

    private String id;
    private Long appliedAmount;
    private Long applyPass;
    private Long applyRefuse;
    private String bizDate;
    private String channel;
    private Long machineRefuse;
    private Long registerAmount;
    private Long registerIdentified;
    private String target;
    private String targetName;
    private Long withdrawAmount;
}
