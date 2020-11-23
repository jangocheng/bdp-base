package com.platform.report.bean;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;
import org.beetl.sql.core.annotatoin.Table;



@Table(name="base-bi.salary_idpbg_count")
public class SalaryIdpbgCount   {
	
	private Integer id ;
	private Long count ;
	private String type ;
	private Date createTime ;
	
	public SalaryIdpbgCount() {
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
