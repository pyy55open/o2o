package com.csy.o2o.service;

import java.util.List;

import com.csy.o2o.dto.ProductCategoryExcution;
import com.csy.o2o.entity.ProductCategory;
import com.csy.o2o.exception.ProductCategoryOperationException;

public interface ProductCategoryService {

	List<ProductCategory> getProductCategoryList(long shopId);
	
	ProductCategoryExcution batchInsertProductCategory(List<ProductCategory> pcList) throws ProductCategoryOperationException;
	
	ProductCategoryExcution deleteProductCategory(long productCategoryID,long shopID);
}
