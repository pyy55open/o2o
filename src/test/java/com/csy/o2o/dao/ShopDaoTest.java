package com.csy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.entity.Area;
import com.csy.o2o.entity.PersonInfo;
import com.csy.o2o.entity.Shop;
import com.csy.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{

	@Autowired
	ShopDao shopDao;
	
	@Test
	public void testQueryShopList(){
		Shop shop = new Shop();
		ShopCategory shopCategory = new ShopCategory();
		ShopCategory parent = new ShopCategory();
		parent.setShopCategoryid(0L);
		shopCategory.setShopCategoryid(2L);
		shopCategory.setParent(parent);
		shop.setShopCategory(shopCategory);
		List<Shop> shopList = shopDao.queryShopList(shop, 0, 5);
		List<Shop> shopList_ = shopDao.queryShopList(shop, 0, 2);
		System.out.println("店铺大小size:"+shopList.size());
		System.out.println("另店铺大小size:"+shopList_.size());
	}
	
	@Test
	@Ignore
	public void testQueryByid(){
		Shop shop = shopDao.queryByShopid(1L);
		assertEquals("餐饮服务",shop.getShopCategory().getShopCategoryname());
	}
	
	@Test
	@Ignore
	public void testInsertShop(){
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserid(1L);
		area.setAreaid(1);
		shopCategory.setShopCategoryid(1L);
		shop.setShopCategory(shopCategory);
		shop.setArea(area);
		shop.setOwer(owner);
		shop.setShopname("玩具超市");
		shop.setPhone("138000000");
		shop.setShopaddr("xxx路20号");
		shop.setShopdesc("test");
		shop.setEnableStatus(1);
		shop.setCreateTime(new Date());
		shop.setUpdateTime(new Date());
		int i = shopDao.insertShop(shop);
		assertEquals(i,1);
	}
	
	@Test
	@Ignore
	public void testUpdateShop(){
		Shop shop = new Shop();
		shop.setShopid(2L);
		
		shop.setShopname("工薪超市");
		
		shop.setCreateTime(new Date());
		shop.setUpdateTime(new Date());
		int i = shopDao.updateShop(shop);
		assertEquals(i,1);
	}
}
