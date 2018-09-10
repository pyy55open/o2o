package com.csy.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.csy.o2o.entity.ProductCategory;

public interface ProductCategoryDao {

	/**
	 * 根据商铺id获取商品类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(Long shopId);
	
	int batchInsertProductCategory(List<ProductCategory> pcList);
	
	int deleteProductCategory(@Param("productCategoryID")Long productCategoryID,@Param("shopID")Long shopID);
}
