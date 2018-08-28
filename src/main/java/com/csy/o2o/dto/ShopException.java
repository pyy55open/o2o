package com.csy.o2o.dto;

import java.util.List;

import com.csy.o2o.entity.Shop;
import com.csy.o2o.enu.ShopEnum;

public class ShopException {
	//状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	//店铺数量
	private int count;
	
	//增删改使用
	private Shop shop;
	
	//
	private List<Shop> shoplist;
	
	public ShopException() {
	}
	
	//操作失败时使用的构造器
	public ShopException(ShopEnum shopEnum) {
		this.state = shopEnum.getState();
		this.stateInfo = shopEnum.getStateInfo();
	}
	//操作成功时使用的构造器
	public ShopException(ShopEnum shopEnum,Shop shop) {
		this.state = shopEnum.getState();
		this.stateInfo = shopEnum.getStateInfo();
		this.shop = shop;
	}
	//操作成功时使用的构造器
	public ShopException(ShopEnum shopEnum,List<Shop> shopList) {
		this.state = shopEnum.getState();
		this.stateInfo = shopEnum.getStateInfo();
		this.shoplist = shopList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShoplist() {
		return shoplist;
	}

	public void setShoplist(List<Shop> shoplist) {
		this.shoplist = shoplist;
	}
	
	
}
