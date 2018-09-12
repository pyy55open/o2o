package com.csy.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csy.o2o.dao.HeadLineDao;
import com.csy.o2o.entity.HeadLine;
import com.csy.o2o.service.HeadLineService;
@Service
public class HeadLineServiceImpl implements HeadLineService{

	@Autowired
	HeadLineDao headLineDao;
		
	@Override
	public List<HeadLine> getHeadLine(HeadLine headLine) {
		return headLineDao.queryHeadLine(headLine);
	}

}
