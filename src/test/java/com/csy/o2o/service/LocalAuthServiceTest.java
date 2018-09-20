package com.csy.o2o.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.dto.LocalAuthExcution;

public class LocalAuthServiceTest extends BaseTest{

	@Autowired
	LocalAuthService localAuthService;
	
	@Test
	public void testUpdatePwd(){
		LocalAuthExcution l =localAuthService.updatePwd(1L, "老王", "123456", "csy665789987", new Date());
		System.out.println(l.getStateInfo());
	}
}