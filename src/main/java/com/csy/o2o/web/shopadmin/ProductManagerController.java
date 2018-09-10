package com.csy.o2o.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.csy.o2o.dto.ImgHolder;
import com.csy.o2o.dto.ProductExcution;
import com.csy.o2o.entity.Product;
import com.csy.o2o.entity.Shop;
import com.csy.o2o.service.ProductService;
import com.csy.o2o.util.HttpServletRequestUtil;
import com.csy.o2o.util.KaptchaUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("shopadmin")
public class ProductManagerController {

	@Autowired
	ProductService productService;
	
	private final int imgMaxCount = 6;
	
	@RequestMapping(value="/addproduct",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addProduct(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		if(!KaptchaUtil.checkKaptcha(request)){
			reMap.put("success", false);
			reMap.put("msg", "验证码错误。");
		}
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;//商品类
		String productStr = HttpServletRequestUtil.getString(request, "productStr");//商品信息json转成字符串
		MultipartHttpServletRequest multipartHttpServletRequest = null;
		ImgHolder ih = null;
		List<ImgHolder> ihList = new ArrayList<ImgHolder>();
		//获取前台传的文件流，给service添加商品必要的参数，缩略图ih  详情图list<ih>
		try{
			CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			//请求中是否存在文件流
			if(commonsMultipartResolver.isMultipart(request)){
				//取出文件流
				multipartHttpServletRequest = (MultipartHttpServletRequest) request;
				//取出缩略图
				CommonsMultipartFile cFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("img");
				ih = new ImgHolder(cFile.getName(), cFile.getInputStream()); 
				//取出详情图list，构建list<imgHolder>
				for (int i = 0; i < imgMaxCount; i++) {
					CommonsMultipartFile detailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("detailImg"+i);
					if(detailFile!=null){
						ImgHolder detailHolder = new ImgHolder(detailFile.getName(), detailFile.getInputStream());
						ihList.add(detailHolder);
					}
				}
			}else{
				reMap.put("success", false);
				reMap.put("msg", "图片不能为空。");
			}
		}catch(Exception e){
			reMap.put("success", false);
			reMap.put("msg", "商品添加失败:"+e.getMessage());
		}
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			reMap.put("success", false);
			reMap.put("msg", "商品添加失败:"+e.getMessage());
		} 
		//缩略图、详情图都有
		if(product!=null&&ih!=null&&ihList!=null){
			try{
				Shop shop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(shop);
				ProductExcution productExcution = productService.addProduct(product, ih, ihList);
				if(productExcution.getState()==1){
					reMap.put("success", true);
					reMap.put("msg",productExcution.getStateInfo());
				}else{
					reMap.put("success", false);
					reMap.put("msg",productExcution.getStateInfo());
				}
			}catch(Exception e){
				reMap.put("success", false);
				reMap.put("msg","商品添加失败:"+e.getMessage());
			}
		}else{
			reMap.put("success", false);
			reMap.put("msg","请填写商品信息。");
		}
		return reMap;
	}
}
