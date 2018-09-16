package com.csy.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/front")
public class FontController {

	@RequestMapping(value="/index",method=RequestMethod.GET)
	private String index(){
		return "front/index";
	}
	
	/**
	 * 店铺详情页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
	private String showShopDetail() {
		return "front/shopdetail";
	}
	
	/**
	 * 店铺列表页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	private String showShopList() {
		return "front/shoplist";
	}
	
	/**
	 * 商品详细页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/productdetail", method = RequestMethod.GET)
	private String showProductDetail() {
		return "front/productdetail";
	}
}
