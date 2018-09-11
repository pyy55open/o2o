package com.csy.o2o.service;

import java.util.List;

import com.csy.o2o.dto.ImgHolder;
import com.csy.o2o.dto.ProductExcution;
import com.csy.o2o.entity.Product;
import com.csy.o2o.exception.ProductOperationException;

public interface ProductService {

	ProductExcution addProduct(Product product,ImgHolder ih,List<ImgHolder> ihList) throws ProductOperationException;
	
	/**
	 * 查询商品信息
	 * @param productID
	 * @return
	 */
	Product queryByProductID(Long productID);
	
	/**
	 * 获取商品列表
	 * @param product
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	ProductExcution queryProductOfShop(Product product,int pageIndex,int pageSize);
	
	/**
	 * 修改商品信息
	 * @param product
	 * @param ih
	 * @param ihList
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExcution modifyProduct(Product product,ImgHolder ih,List<ImgHolder> ihList) throws ProductOperationException;
}
