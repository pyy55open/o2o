package com.csy.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csy.o2o.cache.JedisUtil;
import com.csy.o2o.dao.ShopCategoryDao;
import com.csy.o2o.entity.ShopCategory;
import com.csy.o2o.exception.ShopCategoryOperationException;
import com.csy.o2o.service.ShopCategoryService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService{

	@Autowired
	ShopCategoryDao shopCategoryDao;
	
	@Autowired
	JedisUtil.Keys jedisKeys;
	
	@Autowired
	JedisUtil.Strings jedisStrings;
	
	Logger log = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);
	
	@Override
	public List<ShopCategory> queryShopCategory(ShopCategory shopCategoryCondition) {
		String shopCategoryKeys = SCLISTKEY;
		List<ShopCategory> scList = null;
		ObjectMapper mapper = new ObjectMapper();
		//根据不同的筛选条件拼接不同的key
		if(shopCategoryCondition.getParent()!=null&&shopCategoryCondition.getParent().getShopCategoryid()!=null){
			shopCategoryKeys = shopCategoryKeys+"-parent"+""+shopCategoryCondition.getParent().getShopCategoryid();
		}else if(shopCategoryCondition.getParent() == null){
			shopCategoryKeys = shopCategoryKeys +"-all";
		}else if(shopCategoryCondition.getParent() != null){
			shopCategoryKeys = shopCategoryKeys + "-secondLevel";
		}
		if(!jedisKeys.exists(shopCategoryKeys)){
			scList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
			try {
				String scListStr = mapper.writeValueAsString(scList);
				jedisStrings.set(shopCategoryKeys, scListStr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new ShopCategoryOperationException(e.getMessage());
			}
		}else{
			String scListStr = jedisStrings.get(shopCategoryKeys);
			JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
			try {
				scList = mapper.readValue(scListStr, type);
			} catch (JsonParseException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new ShopCategoryOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new ShopCategoryOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				throw new ShopCategoryOperationException(e.getMessage());
			}
		}
		return scList;
	}

}
