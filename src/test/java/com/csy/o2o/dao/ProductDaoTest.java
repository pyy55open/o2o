package com.csy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.entity.Product;
import com.csy.o2o.entity.ProductCategory;
import com.csy.o2o.entity.Shop;


public class ProductDaoTest extends BaseTest{

	@Autowired
	ProductDao productDao;
	
	@Test
	@Ignore
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
	
	@Test
	@Ignore
	public void testQueryByProductID(){
		Product product =productDao.queryByProductID(1L);
		assertEquals("哈哈",product.getProductname());
	}
	
	@Test
	@Ignore
	public void testQueryProductList(){
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopid(1L);
		product.setShop(shop);
		List<Product> pList = productDao.queryProductList(product, 0, 3);
		assertEquals(3,pList.size());
	}
	
	@Test
	public void testSetProductCategoryNull(){
		int i = productDao.setProductCategoryNull(2L);
		assertEquals(2,i);
	}
}
