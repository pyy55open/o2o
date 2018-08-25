package com.csy.o2o.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.entity.Area;
import com.csy.o2o.entity.PersonInfo;
import com.csy.o2o.entity.Shop;
import com.csy.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{

	@Autowired
	ShopDao shopDao;
	
	public void testInsertShop(){
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserid(1L);
		area.setAreaid(1);
		shopCategory.setShopCategoryid(1L);
	}
}
