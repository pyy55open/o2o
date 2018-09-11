package com.csy.o2o.enu;

public enum ProductStateEnum {

	SUCCESS(1, "插入成功"), INNER_ERROR(-1001, "操作失败"), EMPTY_LIST(-1002, "添加数少于1");
	
	private int state;

	private String stateInfo;
	
	private ProductStateEnum(ProductStateEnum pse) {
		this.state = pse.state;
		this.stateInfo = pse.stateInfo;
	}
	
	private ProductStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	public static ProductStateEnum stateOf(int state){
		for(ProductStateEnum productStateEnum : values()){
			if(productStateEnum.getState() == state){
				return productStateEnum;
			}
		}
		
		return null;
		
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
	
	
}
