package com.csy.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csy.o2o.dao.ShopCategoryDao;
import com.csy.o2o.entity.ShopCategory;
import com.csy.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService{

	@Autowired
	ShopCategoryDao shopCategoryDao;
	
	@Override
	public List<ShopCategory> queryShopCategory(ShopCategory shopCategoryCondition) {
		
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}

}
