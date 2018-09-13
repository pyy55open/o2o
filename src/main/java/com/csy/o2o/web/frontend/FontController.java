package com.csy.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/front")
public class FontController {

	@RequestMapping(value="/index",method=RequestMethod.GET)
	private String index(){
		return "/front/index";
	}
}
