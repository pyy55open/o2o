package com.csy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.entity.HeadLine;

public class HeadLineTest extends BaseTest{
	
	@Autowired
	HeadLineDao headLineDao;
	
	@Test
	public void testQueryHeadLine(){
		HeadLine hl = new HeadLine();
		hl.setEnablestatus(1);
		List<HeadLine> hlList = headLineDao.queryHeadLine(hl);
		assertEquals(2,hlList.size());
	}
}
