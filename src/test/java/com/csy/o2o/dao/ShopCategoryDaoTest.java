package com.csy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{
	
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Test
	@Ignore
	public void testQueryShopCategory(){
		List<ShopCategory> list = shopCategoryDao.queryShopCategory(new ShopCategory());
		assertEquals(1,list.size());
	}
	
	public void testQueryShopList(){
		
	}
}
