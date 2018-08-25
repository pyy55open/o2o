package com.csy.o2o.entity;

import java.util.Date;

public class LocalAuth {
	private Long localAuthid;
	private String name;
	private String password;
	private Date createTime;
	private Date updateTime;
	private PersonInfo personInfo;
	public Long getLocalAuthid() {
		return localAuthid;
	}
	public void setLocalAuthid(Long localAuthid) {
		this.localAuthid = localAuthid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	@Override
	public String toString() {
		return "LocalAuth [localAuthid=" + localAuthid + ", name=" + name + ", password=" + password + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", personInfo=" + personInfo + "]";
	}
	
	
}
