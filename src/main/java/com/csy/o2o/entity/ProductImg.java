package com.csy.o2o.entity;

import java.util.Date;

public class ProductImg {

	private Long productImgid;
	private String imgAddr;
	private String imgDesc;
	private Integer level;
	private Date createTime;
	private Long productid;
	public Long getProductImgid() {
		return productImgid;
	}
	public void setProductImgid(Long productImgid) {
		this.productImgid = productImgid;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getImgDesc() {
		return imgDesc;
	}
	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
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
	public Long getProductid() {
		return productid;
	}
	public void setProductid(Long productid) {
		this.productid = productid;
	}
	
	
}
