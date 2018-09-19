package com.csy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.entity.LocalAuth;
import com.csy.o2o.entity.PersonInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)//控制测试方法执行顺序
public class LocalAuthDaoTest extends BaseTest{
	
	@Autowired
	LocalAuthDao localAuthDao;
	
	@Test
	public void testAInsertLocalAuth(){
		LocalAuth la = new LocalAuth();
		PersonInfo pi = new PersonInfo();
		pi.setUserid(1L);
		la.setPersonInfo(pi);
		la.setName("csy41114");
		la.setPassword("123456");
		la.setCreateTime(new Date());
		int i =localAuthDao.insertLocalAuth(la);
		assertEquals(1,i);
	}
	
	@Test
	public void testBLoginByNamePasswd(){
		String name = "csy41114";
		String password = "123456";
		LocalAuth  la = localAuthDao.loginByNamePasswd(name, password);
		assertEquals("老王",la.getPersonInfo().getName());
	}
}
