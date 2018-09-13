package com.csy.o2o.web.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csy.o2o.entity.HeadLine;
import com.csy.o2o.entity.ShopCategory;
import com.csy.o2o.service.HeadLineService;
import com.csy.o2o.service.ShopCategoryService;

@Controller
@RequestMapping("/front")
public class MainPageController {
	
	Logger log = LoggerFactory.getLogger(MainPageController.class);
	
	@Autowired
	ShopCategoryService shopCategoryService;
	
	@Autowired
	HeadLineService headLineService;
	
	@RequestMapping(value="/getheadline",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getHeadLine(){
		Map<String, Object> reMap = new HashMap<String,Object>();
		List<ShopCategory> scList = new ArrayList<ShopCategory>();
		List<HeadLine> hlList = new ArrayList<HeadLine>();
		try{
			scList=shopCategoryService.queryShopCategory(null);
			reMap.put("scList", scList);
			reMap.put("success", true);
		}catch(Exception e){
			reMap.put("success", false);
			reMap.put("msg", "查询一级店铺类别异常:"+e.getMessage());
			log.error("查询一级店铺类别异常:"+e.getMessage());
		}
		try{
			HeadLine headLine = new HeadLine();
			headLine.setEnablestatus(1);
			hlList = headLineService.getHeadLine(headLine);
			reMap.put("hlList", hlList);
		}catch(Exception e){
			reMap.put("success", false);
			reMap.put("msg", "查询头条异常:"+e.getMessage());
			log.error("查询头条异常:"+e.getMessage());
		}
		return reMap;
	}
}
