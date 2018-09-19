package com.csy.o2o.service;

import java.util.List;

import com.csy.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	public static final String SCLISTKEY ="shopCategoryList";
	
	public List<ShopCategory> queryShopCategory(ShopCategory shopCategoryCondition);
}
