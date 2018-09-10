package com.csy.o2o.dto;

import java.util.List;

import com.csy.o2o.entity.ProductCategory;
import com.csy.o2o.enu.ProductCategoryStateEnum;

public class ProductCategoryExcution {

	//状态
		private int state;
		
		//状态标识
		private String stateInfo;
		
		//店铺数量
		private int count;
		
		//增删改使用
		private ProductCategory productCategory;
		
		//
		private List<ProductCategory> productCategorylist;
		
		public ProductCategoryExcution() {
		}
		
		public ProductCategoryExcution(ProductCategoryStateEnum pcse) {
			this.state = pcse.getState();
			this.stateInfo = pcse.getStateInfo();
		}
		
		public ProductCategoryExcution(ProductCategoryStateEnum pcse,List<ProductCategory> pcList) {
			this.state = pcse.getState();
			this.stateInfo = pcse.getStateInfo();
			this.productCategorylist = pcList;
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

		public ProductCategory getProductCategory() {
			return productCategory;
		}

		public void setProductCategory(ProductCategory productCategory) {
			this.productCategory = productCategory;
		}

		public List<ProductCategory> getProductCategorylist() {
			return productCategorylist;
		}

		public void setProductCategorylist(List<ProductCategory> productCategorylist) {
			this.productCategorylist = productCategorylist;
		}
		
		
}
