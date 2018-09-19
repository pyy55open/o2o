package com.csy.o2o.service;

import java.util.List;

import com.csy.o2o.entity.Area;

public interface AreaService {
	
	public final String AREALISTKEY = "arealist";
	
	public List<Area> getArea();
}
