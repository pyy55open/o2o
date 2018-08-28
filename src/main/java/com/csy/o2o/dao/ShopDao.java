package com.csy.o2o.dao;

import com.csy.o2o.entity.Shop;

public interface ShopDao {

	/**
	 * 新增(注册)店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * 修改(变更)店铺
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
