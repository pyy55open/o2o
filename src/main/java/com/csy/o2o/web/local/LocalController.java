package com.csy.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/local")
public class LocalController {

	@RequestMapping(value="/loginpage",method=RequestMethod.GET)
	public String login(){
		return "local/login";
	}
	
	@RequestMapping(value="/changepsw",method=RequestMethod.GET)
	public String changepwd(){
		return "local/changepwd";
	}
}
