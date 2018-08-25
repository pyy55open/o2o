package com.csy.o2o.entity;

import java.util.Date;

public class ShopCategory {

	private Long shopCategoryid;
	private String shopCategoryname;
	private String shopCategorydesc;
	private String shopCategoryimg;
	private Integer level;
	private Date createTime;
	private Date updateTime;
	private ShopCategory parent;
	public Long getShopCategoryid() {
		return shopCategoryid;
	}
	public void setShopCategoryid(Long shopCategoryid) {
		this.shopCategoryid = shopCategoryid;
	}
	public String getShopCategoryname() {
		return shopCategoryname;
	}
	public void setShopCategoryname(String shopCategoryname) {
		this.shopCategoryname = shopCategoryname;
	}
	public String getShopCategorydesc() {
		return shopCategorydesc;
	}
	public void setShopCategorydesc(String shopCategorydesc) {
		this.shopCategorydesc = shopCategorydesc;
	}
	public String getShopCategoryimg() {
		return shopCategoryimg;
	}
	public void setShopCategoryimg(String shopCategoryimg) {
		this.shopCategoryimg = shopCategoryimg;
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
	public ShopCategory getParent() {
		return parent;
	}
	public void setParent(ShopCategory parent) {
		this.parent = parent;
	}
	
	
}
