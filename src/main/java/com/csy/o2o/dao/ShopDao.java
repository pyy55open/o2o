package com.csy.o2o.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.csy.o2o.entity.Shop;

public interface ShopDao {

	/**
	 * 店铺分页查询 可根据owner 类别 区域查询
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shop,@Param("rowIndex")int rowIndex
			,@Param("pageSize")int pageSize);
	
	/**
	 * 查询店铺数量
	 * @param shop
	 * @return
	 */
	int queryShopCount(@Param("shopCondition")Shop shop);
	
	/**
	 * 根据店铺id查店铺信息
	 */
	Shop queryByShopid(Long shopid);
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
