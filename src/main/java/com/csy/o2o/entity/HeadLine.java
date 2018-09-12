package com.csy.o2o.entity;

import java.util.Date;

public class HeadLine {

	private Long lineid;
	private String lineName;
	private String lineLink;
	private String lineImg;
	private Integer level;
	//0无效  1有效
	private Integer enablestatus;
	private Date createTime;
	private Date updateTime;
	public Long getLineid() {
		return lineid;
	}
	public void setLineid(Long lineid) {
		this.lineid = lineid;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getLineLink() {
		return lineLink;
	}
	public void setLineLink(String lineLink) {
		this.lineLink = lineLink;
	}
	public String getLineImg() {
		return lineImg;
	}
	public void setLineImg(String lineImg) {
		this.lineImg = lineImg;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getEnablestatus() {
		return enablestatus;
	}
	public void setEnablestatus(Integer enablestatus) {
		this.enablestatus = enablestatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
