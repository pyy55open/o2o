package com.csy.o2o.dto;

import java.util.List;

import com.csy.o2o.entity.Product;
import com.csy.o2o.enu.ProductStateEnum;


public class ProductExcution {

	//状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	//店铺数量
	private int count;
	
	//增删改使用
	private Product product;
	
	//
	private List<Product> productlist;
	
	public ProductExcution() {
	}
	
	public ProductExcution(ProductStateEnum pcse) {
		this.state = pcse.getState();
		this.stateInfo = pcse.getStateInfo();
	}
	
	public ProductExcution(ProductStateEnum pcse,Product product) {
		this.state = pcse.getState();
		this.stateInfo = pcse.getStateInfo();
		this.product = product;
	}
	
	public ProductExcution(ProductStateEnum pcse,List<Product> pcList) {
		this.state = pcse.getState();
		this.stateInfo = pcse.getStateInfo();
		this.productlist = pcList;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductlist() {
		return productlist;
	}

	public void setProductlist(List<Product> productlist) {
		this.productlist = productlist;
	}
	
	
}
