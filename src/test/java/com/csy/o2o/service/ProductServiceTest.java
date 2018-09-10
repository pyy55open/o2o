package com.csy.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.dto.ImgHolder;
import com.csy.o2o.dto.ProductExcution;
import com.csy.o2o.entity.Product;
import com.csy.o2o.entity.ProductCategory;
import com.csy.o2o.entity.Shop;

public class ProductServiceTest extends BaseTest{

	@Autowired
	ProductService ps;
	
	@Test
	public void testAddProduct() throws FileNotFoundException{
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopid(2L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryid(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductname("测试商品添加service");
		File file = new File("E:/img/1.png");
		InputStream is = new FileInputStream(file);
		ImgHolder ih = new ImgHolder(file.getName(), is);
		
		File file1 = new File("E:/img/2.jpg");
		InputStream is1 = new FileInputStream(file1);
		File file2 = new File("E:/img/3.jpg");
		InputStream is2 = new FileInputStream(file2);
		List<ImgHolder> ihList = new ArrayList<ImgHolder>();
		ihList.add(new ImgHolder(file1.getName(), is1));
		ihList.add(new ImgHolder(file2.getName(), is2));
		ProductExcution pe = ps.addProduct(product, ih, ihList);
		assertEquals("插入成功",pe.getStateInfo());
	}
}
