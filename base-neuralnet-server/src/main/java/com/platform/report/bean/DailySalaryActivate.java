package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

@Getter
@Setter
@Table(name="base-bi.daily_salary_activate")
public class DailySalaryActivate {
	
	private Long id ;
	private Long activateSalaryAmount ;
	private Long totalRegisterAmount ;
	private String bizDate ;
	private Date beginTime ;
	private Date createTime ;
	private Date endTime ;
}
