package com.csy.o2o.enu;

public enum ShopEnum {
	CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),PASS(2,"通过认证"),INNER_ERROR(-1001,"内部错误")
	,NULL_SHOPID(-1002,"店铺id为空"),NULL_SHOP(-1003,"店铺信息为空");
	
	private int state;
	private String stateInfo;
	private ShopEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	public static ShopEnum stateOf(int state){
		for(ShopEnum shopEnum : values()){
			if(shopEnum.getState() == state){
				return shopEnum;
			}
		}
		
		return null;
		
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	
}
