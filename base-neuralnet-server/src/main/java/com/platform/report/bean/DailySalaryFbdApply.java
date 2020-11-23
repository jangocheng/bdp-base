package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

@Getter
@Setter
@Table(name="base-bi.daily_salary_fbd_apply")
public class DailySalaryFbdApply   {
	
	private Long id ;
	private Long fbdApprovedAmount ;
	private Long fbdRegisterAmount ;
	private String bizDate ;
	private Date beginTime ;
	private Date createTime ;
	private Date endTime ;
}
