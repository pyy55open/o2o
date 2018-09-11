package com.csy.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.csy.o2o.entity.Product;

public interface ProductDao {

	int addProduct(Product product);
	
	/**
	 * 修改商品信息
	 * @param product
	 * @return
	 */
	int modifyProduct(Product product);
	
	/**
	 * 根据ID查询商品信息
	 * @param productID
	 * @return
	 */
	Product queryByProductID(Long productID);
	
	/**
	 * 查询店铺下的商品列表
	 * @param shopID
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition")Product product,@Param("rowIndex")int rowIndex
			,@Param("pageSize")int pageSize);
	
	/**
	 * 查询商品总数
	 * @param product
	 * @return
	 */
	int quertCountOfProduct(@Param("productCondition")Product product);
}
