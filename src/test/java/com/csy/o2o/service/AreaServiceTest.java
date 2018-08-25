package com.csy.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.csy.o2o.BaseTest;
import com.csy.o2o.entity.Area;

public class AreaServiceTest extends BaseTest{

	@Autowired
	AreaService areaService;
	
	@Test
	public void testGetArea(){
		List<Area> list = areaService.getArea();
		assertEquals("问",list.get(0).getAreaname());
	}
}
