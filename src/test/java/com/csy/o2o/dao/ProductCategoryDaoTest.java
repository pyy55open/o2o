package com.csy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.entity.ProductCategory;

//@FixMethodOrder(MethodSorters.JVM)//JVM得到方法的顺序
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//控制测试方法执行顺序
public class ProductCategoryDaoTest extends BaseTest{

	@Autowired
	ProductCategoryDao productCategoryDao;
	
	@Test
	@Ignore
	public void testQueryProductCategoryList(){
		System.out.println(productCategoryDao.queryProductCategoryList(2L).size());
	}
	
	@Test
	@Ignore
	public void testBatchInsertProductCategory(){
		ProductCategory pc1 = new ProductCategory();
		pc1.setLevel(1);
		pc1.setProductCategoryname("玩的");
		pc1.setShopid(2L);
		pc1.setCreateTime(new Date());
		ProductCategory pc2 = new ProductCategory();
		pc2.setLevel(1);
		pc2.setProductCategoryname("吃的");
		pc2.setShopid(2L);
		pc2.setCreateTime(new Date());
		List<ProductCategory> list = new ArrayList<>();
		list.add(pc1);
		list.add(pc2);
		int i = productCategoryDao.batchInsertProductCategory(list);
		assertEquals(2,i);
	}
	
	@Test
	public void testDeleteProductCategory(){
		long i = 2L;
		List<ProductCategory> pcList = productCategoryDao.queryProductCategoryList(i);
		if(pcList != null && pcList.size() > 0){
			for (int j = 0; j < pcList.size(); j++) {
				ProductCategory pc = pcList.get(j);
				if("玩的".equals(pc.getProductCategoryname())||"吃的".equals(pc.getProductCategoryname())){
					int num = productCategoryDao.deleteProductCategory(pc.getProductCategoryid(), i);
					assertEquals(1,num);
				}
			}
		}
	}
}
