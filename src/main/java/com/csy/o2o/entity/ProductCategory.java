package com.csy.o2o.entity;

import java.util.Date;

public class ProductCategory {

	private Long productCategoryid;
	private Long shopid;
	private String productCategoryname;
	private Integer level;
	private Date createTime;
	public Long getProductCategoryid() {
		return productCategoryid;
	}
	public void setProductCategoryid(Long productCategoryid) {
		this.productCategoryid = productCategoryid;
	}
	public Long getShopid() {
		return shopid;
	}
	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}
	public String getProductCategoryname() {
		return productCategoryname;
	}
	public void setProductCategoryname(String productCategoryname) {
		this.productCategoryname = productCategoryname;
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
	
	
}
