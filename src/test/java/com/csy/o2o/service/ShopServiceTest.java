package com.csy.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.dto.ImgHolder;
import com.csy.o2o.dto.ShopException;
import com.csy.o2o.entity.Area;
import com.csy.o2o.entity.PersonInfo;
import com.csy.o2o.entity.Shop;
import com.csy.o2o.entity.ShopCategory;
import com.csy.o2o.enu.ShopEnum;

public class ShopServiceTest extends BaseTest{
	
	@Autowired
	ShopService shopService;
	
	@Test
	public void testGetShopList(){
		Shop shop = new Shop();
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryid(1L);
		shop.setShopCategory(shopCategory);
		ShopException shopException = shopService.getShopList(shop, 0, 5);
		System.out.println("店铺大小size:"+shopException.getShoplist().size());
		System.out.println("店铺大小size:"+shopException.getCount());
	}
	
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException{
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
		shop.setShopname("测试addShop改造");
		shop.setPhone("138888886");
		shop.setShopaddr("xxx路26号");
		shop.setShopdesc("test");
		File file = new File("E:/scar.jpg");
		InputStream fileIS = new FileInputStream(file);
		ImgHolder ih = new ImgHolder(file.getName(), fileIS);
		ShopException shopException = shopService.addShop(shop,ih);
		assertEquals(ShopEnum.CHECK.getState(),shopException.getState());
	}
}
