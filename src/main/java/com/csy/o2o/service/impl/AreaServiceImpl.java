package com.csy.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csy.o2o.cache.JedisUtil;
import com.csy.o2o.dao.AreaDao;
import com.csy.o2o.entity.Area;
import com.csy.o2o.exception.AreaOperationException;
import com.csy.o2o.service.AreaService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AreaServiceImpl implements AreaService{

	@Autowired
	AreaDao areaDao;
	
	@Autowired
	JedisUtil.Keys jedisKeys;
	
	@Autowired
	JedisUtil.Strings jedisStrings;
	
	private String AREALISTKEY = "arealist";
	
	private Logger log = LoggerFactory.getLogger(AreaServiceImpl.class);
	
	public List<Area> getArea() {
		String keys = AREALISTKEY;
		List<Area> areaList = null;
		ObjectMapper mapper = new ObjectMapper();
		//如果不存在key
		if(!jedisKeys.exists(keys)){
			areaList = areaDao.queryArea();
			try {
				String areaListStr = mapper.writeValueAsString(areaList);
				jedisStrings.set(keys, areaListStr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
		}else{
			String jsonStr = jedisStrings.get(keys);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				areaList = mapper.readValue(jsonStr, javaType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
		}
		return areaList;
	}

}
