package com.csy.o2o.dao;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.entity.Product;
import com.csy.o2o.entity.ProductCategory;
import com.csy.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest{

	@Autowired
	ProductDao productDao;
	
	@Test
	public void testAinsertProduct(){
		Product p1 = new Product();
		Shop shop = new Shop();
		shop.setShopid(2L);
		ProductCategory pc= new ProductCategory();
		pc.setProductCategoryid(1L);
		p1.setShop(shop);
		p1.setProductCategory(pc);
		p1.setProductname("商品1");
		p1.setPromotionprice("1");
		p1.setNormalprice("1");
		p1.setEnableStatus(1);
		
		Product p2 = new Product();
		p2.setShop(shop);
		p2.setProductCategory(pc);
		p2.setProductname("商品1");
		p2.setPromotionprice("1");
		p2.setNormalprice("1");
		p2.setEnableStatus(1);
		
		int i = productDao.addProduct(p1);
		int j = productDao.addProduct(p2);
		assertEquals(1,i);
		assertEquals(1,j);
	}
}
