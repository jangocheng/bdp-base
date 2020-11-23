package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;


@Getter
@Setter
@Table(name = "base-bi.business_register_day")
public class BusinessRegisterDay {

    private String id;
    private Long appliedAmount;
    private Long applyCancel;
    private Long applyIng;
    private Long applyPass;
    private Long applyRefuse;
    private String bizDate;
    private String channel;
    private Long registerAmount;
    private Long registerIdentified;
    private String target;
    private String targetName;

}
