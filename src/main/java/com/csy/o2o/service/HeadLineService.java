package com.csy.o2o.service;

import java.util.List;

import com.csy.o2o.entity.HeadLine;

public interface HeadLineService {
	
	public final String HEADLISTKEY = "headlist";
	
	/**
	 * 获取头条
	 * @param headLine
	 * @return
	 */
	List<HeadLine> getHeadLine(HeadLine headLine);
}
