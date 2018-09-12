package com.csy.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.runners.Parameterized.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.csy.o2o.dto.ImgHolder;
import com.csy.o2o.dto.ProductExcution;
import com.csy.o2o.entity.Product;
import com.csy.o2o.entity.ProductCategory;
import com.csy.o2o.entity.Shop;
import com.csy.o2o.enu.ProductStateEnum;
import com.csy.o2o.service.ProductCategoryService;
import com.csy.o2o.service.ProductService;
import com.csy.o2o.util.HttpServletRequestUtil;
import com.csy.o2o.util.KaptchaUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("shopadmin")
public class ProductManagerController {

	Logger log = LoggerFactory.getLogger(ProductManagerController.class);
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductCategoryService productCategoryService;
	
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
				ih = handleImg(request,ih, ihList);
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

	private ImgHolder handleImg(HttpServletRequest request,ImgHolder ih, List<ImgHolder> ihList) throws IOException {
		MultipartHttpServletRequest multipartHttpServletRequest;
		//取出文件流
		multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		//取出缩略图
		CommonsMultipartFile cFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("img");
		if(cFile!=null){
			ih = new ImgHolder(cFile.getOriginalFilename(), cFile.getInputStream()); 
		}
		//取出详情图list，构建list<imgHolder>
		for (int i = 0; i < imgMaxCount; i++) {
			CommonsMultipartFile detailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("detailImg"+i);
			if(detailFile!=null){
				ImgHolder detailHolder = new ImgHolder(detailFile.getName(), detailFile.getInputStream());
				ihList.add(detailHolder);
			}else{
				break;
			}
		}
		return ih;
	}
	
	@RequestMapping(value="/getproductlist",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductList(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		Shop shop = (Shop) request.getSession().getAttribute("currentShop");
		//页面传来的页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//页面传来的页面条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		try{
			if(pageIndex>-1&&pageSize>-1&&shop!=null){
				Long productCategoryID = HttpServletRequestUtil.getLong(request, "productCategoryID");
				String productName = HttpServletRequestUtil.getString(request, "productName");
				Product product = new Product();
				ProductCategory productCategory =new ProductCategory();
				if(productCategoryID>-1){
					productCategory.setShopid(shop.getShopid());
				}
				if(productName!=null){
					product.setProductname(productName);
				}
				product.setProductCategory(productCategory);
				ProductExcution productExcution = productService.queryProductOfShop(product, pageIndex, pageSize);
				if(productExcution!=null && productExcution.getProductlist().size()>0){
					reMap.put("productList", productExcution.getProductlist());
					reMap.put("count", productExcution.getCount());
					reMap.put("success", true);
				}else{
					reMap.put("success", true);
					reMap.put("productList", productExcution.getProductlist());
					reMap.put("msg", "该店铺没有商品");
				}
			}
		}catch(Exception e){
			reMap.put("success", false);
			reMap.put("msg", "获取商品列表失败:"+e.getMessage());
		}
		return reMap;
	}
	
	@RequestMapping(value="/getproductbyid",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductByID(@RequestParam Long productID){
		Map<String, Object> reMap = new HashMap<String,Object>();
		if(productID>-1){
				Product product = productService.queryByProductID(productID);
				List<ProductCategory> pcList = productCategoryService.getProductCategoryList(product.getShop().getShopid());
				reMap.put("pcList", pcList);
				reMap.put("product", product);
				reMap.put("success", true);
		}else{
			reMap.put("success", false);
			reMap.put("msg", "未查到相关商品信息。");
		}
		return reMap;
	}
	
	@RequestMapping(value="/modifyproduct",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> modifyProduct(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		boolean isChange = HttpServletRequestUtil.getBoolean(request, "isChange");//页面传来区分是否为变更的标识
		//如果是变更就跳过验证码
		if(!isChange&&!KaptchaUtil.checkKaptcha(request)){
			reMap.put("success", false);
			reMap.put("msg", "验证码输入有误。");
			return reMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		ImgHolder holder = null;
		List<ImgHolder> ihList = new ArrayList<ImgHolder>();
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try{
			if(multipartResolver.isMultipart(request)){
				holder = handleImg(request,holder, ihList);
			}
		}catch(Exception e){
			reMap.put("success", false);
			reMap.put("msg", "图片上传失败:"+e.getMessage());
			return reMap;
		}
		try{
			//request中product对象的json，转成str，objectmapper转成对应实体对象
			String productStr = HttpServletRequestUtil.getString(request, "productStr");
			product = mapper.readValue(productStr, Product.class);
		}catch(Exception e){
			reMap.put("success", false);
			reMap.put("msg", "商品更新失败:"+e.getMessage());
			log.error(e.getMessage());
			return reMap;
		}
		if(product!=null){
			//获取当前店铺
			Shop shop = (Shop) request.getSession().getAttribute("currentShop");
			product.setShop(shop);
			ProductExcution productExcution = productService.modifyProduct(product, holder, ihList);
			if(productExcution.getState()==ProductStateEnum.SUCCESS.getState()){
				reMap.put("success", true);
				return reMap;
			}else{
				reMap.put("success", false);
				reMap.put("msg", productExcution.getStateInfo());
				return reMap;
			}
		}else{
			reMap.put("success", false);
			reMap.put("msg", "请填写商品信息。");
			return reMap;
		}
	}
}
