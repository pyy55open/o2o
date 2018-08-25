package com.csy.o2o.entity;

import java.util.Date;

public class PersonInfo {
	private Long userid;
	private String name;
	private String photo;
	private String email;
	private String gender;
	private Integer enablestatus;
	//1.顾客 2.卖家 3.管理员
	private Integer usertype;
	private Date createTime;
	private Date updateTime;
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getEnablestatus() {
		return enablestatus;
	}
	public void setEnablestatus(Integer enablestatus) {
		this.enablestatus = enablestatus;
	}
	public Integer getUsertype() {
		return usertype;
	}
	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
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
		return "PersonInfo [userid=" + userid + ", name=" + name + ", photo=" + photo + ", email=" + email + ", gender="
				+ gender + ", enablestatus=" + enablestatus + ", usertype=" + usertype + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
	
}
