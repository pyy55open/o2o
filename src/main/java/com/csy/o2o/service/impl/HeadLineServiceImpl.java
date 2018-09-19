package com.csy.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csy.o2o.cache.JedisUtil;
import com.csy.o2o.dao.HeadLineDao;
import com.csy.o2o.entity.HeadLine;
import com.csy.o2o.exception.HeadLineOperationException;
import com.csy.o2o.service.HeadLineService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class HeadLineServiceImpl implements HeadLineService{

	@Autowired
	HeadLineDao headLineDao;
	
	@Autowired
	JedisUtil.Keys jedisKeys;//redis键
	
	@Autowired
	JedisUtil.Strings jedisStrings;//redis值字符串
	Logger log = LoggerFactory.getLogger(HeadLineServiceImpl.class);
		
	@Override
	public List<HeadLine> getHeadLine(HeadLine headLine) {
		String keys = HEADLISTKEY;
		List<HeadLine> list = null;
		ObjectMapper mapper = new ObjectMapper();
		if(headLine!=null&&headLine.getEnablestatus()!=null){
			keys = keys +"-"+headLine.getEnablestatus();
		}
		if(!jedisKeys.exists(keys)){
			list = headLineDao.queryHeadLine(headLine);
			try {
				String headLineStr = mapper.writeValueAsString(list);
				jedisStrings.set(keys, headLineStr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			}
		}else{
			String headLineStr = jedisStrings.get(keys);
			JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
			try {
				list = mapper.readValue(headLineStr, type);
			} catch (JsonParseException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new HeadLineOperationException(e.getMessage());
			}
		}
		return list;
	}

}
