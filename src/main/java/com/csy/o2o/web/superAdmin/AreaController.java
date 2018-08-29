package com.csy.o2o.web.superadmin;

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

import com.csy.o2o.entity.Area;
import com.csy.o2o.service.AreaService;

@Controller
@RequestMapping("/superadmin")
public class AreaController {

	Logger log = LoggerFactory.getLogger(AreaController.class);
	
	@Autowired
	AreaService areaService;
	@RequestMapping(value="/listarea",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listArea(){
		log.info("====进入listArea=====");
		Long start =System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String,Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.getArea();
			map.put("rows", list);
			map.put("total", list.size());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("succes", false);
			map.put("errorMsg", e.toString());
		}
		Long end =System.currentTimeMillis();
		log.error("====error测试");
		log.debug("耗时[{}]",end-start);;
		log.info("======结束listArea=====");
		return map;
	}
	
}
