package com.csy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest{

	@Autowired
	ProductImgDao productImgDao;
	
	@Test
	public void testAbatchInsertProductImg(){
		ProductImg pi = new ProductImg();
		pi.setImgAddr("图片1");
		pi.setImgDesc("图片1");
		pi.setLevel(2);
		pi.setProductid(1L);
		
		ProductImg pi2 = new ProductImg();
		pi2.setImgAddr("图片2");
		pi2.setImgDesc("图片2");
		pi2.setLevel(2);
		pi2.setProductid(1L);
		
		List<ProductImg> piList = new ArrayList<ProductImg>();
		piList.add(pi);
		piList.add(pi2);
		int i = productImgDao.batchInsertProductImg(piList);
		assertEquals(2,i);
	}
	
	@Test
	public void testBqueryProductImgList(){
		List<ProductImg> piList = productImgDao.queryProductImgList(1L);
		assertEquals(2,piList.size());
	}
	
	@Test
	public void testCdeleteProductImgByProductId(){
		int i = productImgDao.deleteProductImgByProductId(1L);
		assertEquals(2,i);
	}
}
