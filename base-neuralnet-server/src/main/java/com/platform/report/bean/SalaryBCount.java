package com.platform.report.bean;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;


@Table(name="base-bi.salary_b_count")
public class SalaryBCount   {
	
	private Integer id ;
	private Long count ;
	private String type ;
	private Date createTime ;
	
	public SalaryBCount() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Long getCount(){
		return  count;
	}
	public void setCount(Long count ){
		this.count = count;
	}
	
	public String getType(){
		return  type;
	}
	public void setType(String type ){
		this.type = type;
	}
	
	public Date getCreateTime(){
		return  createTime;
	}
	public void setCreateTime(Date createTime ){
		this.createTime = createTime;
	}
	

}
