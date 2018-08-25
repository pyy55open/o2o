package com.csy.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csy.o2o.dao.AreaDao;
import com.csy.o2o.entity.Area;
import com.csy.o2o.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService{

	@Autowired
	AreaDao areaDao;
	
	public List<Area> getArea() {
		return areaDao.queryArea();
	}

}
