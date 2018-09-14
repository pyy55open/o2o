package com.csy.o2o.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csy.o2o.entity.Product;
import com.csy.o2o.service.ProductService;
import com.csy.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/front")
public class ProductController {

	Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value="/getproductdetail",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductDetail(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		Long productID = HttpServletRequestUtil.getLong(request, "productID");
		if(productID>-1){
			log.info("=====查询商品详细信息，productID:"+productID);
			//mapper中的sql查出了详情图信息
			Product product = productService.queryByProductID(productID);
			reMap.put("product", product);
			reMap.put("success", true);
		}else{
			reMap.put("success", false);
			reMap.put("msg", "商品ID为空");
			log.warn("======查询商品详细信息，商品ID为空");
		}
		return reMap;
	}
}
