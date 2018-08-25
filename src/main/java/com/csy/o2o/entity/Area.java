package com.csy.o2o.entity;

import java.util.Date;

public class Area {

	//主键
	private Integer areaid;
	//区域
	private String areaname;
	//级别
	private Integer level;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	
	public Integer getAreaid() {
		return areaid;
	}
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
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
	@Override
	public String toString() {
		return "Area [areaid=" + areaid + ", areaname=" + areaname + ", level=" + level + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
}
