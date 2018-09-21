package com.csy.o2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.csy.o2o.dto.ShopException;
import com.csy.o2o.entity.Area;
import com.csy.o2o.entity.PersonInfo;
import com.csy.o2o.entity.Shop;
import com.csy.o2o.entity.ShopCategory;
import com.csy.o2o.enu.ShopEnum;
import com.csy.o2o.service.AreaService;
import com.csy.o2o.service.ShopCategoryService;
import com.csy.o2o.service.ShopService;
import com.csy.o2o.util.HttpServletRequestUtil;
import com.csy.o2o.util.KaptchaUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/shopadmin",method=RequestMethod.GET)
public class ShopManagerController {

	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value="/getshopmanageinfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopManagerInfo(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId <= 0){
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if(currentShopObj==null){
				reMap.put("redirect", true);
				reMap.put("url", "/o2o/shopadmin/shoplist");
			}else{
				Shop currentShop = (Shop) currentShopObj;
				reMap.put("redirect", false);
				reMap.put("shopId", currentShop.getShopid());
			}
		}else{
			Shop currentShop = new Shop();
			currentShop.setShopid(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			reMap.put("redirect", false);
		}
		return reMap;
	}
	
	@RequestMapping(value="/getshoplist",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getShopList(HttpServletRequest request){
		Map<String,Object> reMap = new HashMap<String,Object>();
		PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
		Shop shop = new Shop();
		shop.setOwer(owner);
		try{
			ShopException shopException = shopService.getShopList(shop, 0, 100);
			reMap.put("shopList", shopException.getShoplist());
			reMap.put("user", owner);
			reMap.put("success", true);
		}catch(Exception e){
			reMap.put("success", false);
			reMap.put("msg", "获取店铺列表异常:"+e.getMessage());
		}
		return reMap;
	}
	
	@RequestMapping(value = "/getshopbyid",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getShopByid(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		Long shopId = Long.valueOf(request.getParameter("shopId"));
		if(shopId > -1){
			try{
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getArea();
				reMap.put("shop", shop);
				reMap.put("areaList", areaList);
				reMap.put("success", true);
			}catch(Exception e){
				reMap.put("success", false);
				reMap.put("msg", "店铺信息查询异常:"+e.getMessage());
			}
		}else {
			reMap.put("success", false);
			reMap.put("msg", "未查到相关店铺信息。");
		}
		return reMap;
	}
	
	@RequestMapping(value="/getshopinitinfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopInitInfo(){
		Map<String, Object> map = new HashMap<String,Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try{
			shopCategoryList = shopCategoryService.queryShopCategory(new ShopCategory());
			areaList = areaService.getArea();
			map.put("shopCategoryList", shopCategoryList);
			map.put("areaList", areaList);
			map.put("success", true);
		}catch(Exception e){
			map.put("success", false);
			map.put("error", e.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/addshop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerShop(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String ,Object>();
		if(!KaptchaUtil.checkKaptcha(request)){
			reMap.put("success", false);
			reMap.put("msg", "验证码错误");
		}
		//1.接受并转换参数信息，set店铺信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper omapper = new ObjectMapper();
		Shop shop = null;
		try{
			shop = omapper.readValue(shopStr, Shop.class);
			
		}catch(Exception e){
			reMap.put("succes", false);
			reMap.put("msg", e.getMessage());
		}
		//1.2接受并出路图片信息
		CommonsMultipartFile cFile = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)){
			MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
			cFile = (CommonsMultipartFile) mulRequest.getFile("imgStr");
		}else{
			reMap.put("succes", false);
			reMap.put("msg", "图片上传失败");
		}
		//2.注册
		if(shop != null && cFile != null){
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwer(owner);
			ShopException shopException;
			try {
				ImgHolder ih = new ImgHolder(cFile.getOriginalFilename(), cFile.getInputStream());
				shopException = shopService.addShop(shop,ih);
				if(shopException.getState() == ShopEnum.CHECK.getState()){
					reMap.put("succes", true);
					reMap.put("msg", "店铺创建成功！");
					//用户有权限修改的店铺列表
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if(shopList.size() == 0 || shopList == null){
						shopList = new ArrayList<Shop>();
					}
					shopList.add(shopException.getShop());
					request.getSession().setAttribute("shopList", shopList);
				}else{
					reMap.put("succes", true);
					reMap.put("msg", "店铺创建失败！");
				}
			} catch (IOException e) {
				reMap.put("succes", false);
				reMap.put("msg", e.getMessage());
							
			}
		}else{
			reMap.put("succes", false);
			reMap.put("msg", "店铺信息为空");
		}
		return reMap;
	}
	
	@RequestMapping(value="/modifyShop", method = RequestMethod.POST)
	public Map<String, Object> modifyShop(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String ,Object>();
		if(!KaptchaUtil.checkKaptcha(request)){
			reMap.put("success", false);
			reMap.put("msg", "验证码错误");
			return reMap;
		}
		//1.接受并转换参数信息，set店铺信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper omapper = new ObjectMapper();
		Shop shop = null;
		try{
			shop = omapper.readValue(shopStr, Shop.class);
			
		}catch(Exception e){
			reMap.put("succes", false);
			reMap.put("msg", e.getMessage());
			return reMap;
		}
		//1.2接受并出路图片信息
		CommonsMultipartFile cFile = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)){
			MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
			cFile = (CommonsMultipartFile) mulRequest.getFile("imgStr");
		}
		//2.修改店铺信息
		if(shop != null && shop.getShopid()!=null){
			ShopException shopException;
			try {
				if(cFile != null){
					ImgHolder ih = new ImgHolder(cFile.getOriginalFilename(), cFile.getInputStream());
					shopException = shopService.addShop(shop,ih);
				}else{
					shopException = shopService.addShop(shop,null);
				}
				if(shopException.getState() == ShopEnum.CHECK.getState()){
					reMap.put("succes", true);
					reMap.put("msg", "店铺修改成功！");
					return reMap;
				}else{
					reMap.put("succes", true);
					reMap.put("msg", "店铺修改失败！");
					return reMap;
				}
			} catch (IOException e) {
				reMap.put("succes", false);
				reMap.put("msg", e.getMessage());
				return reMap;			}
		}else{
			reMap.put("succes", false);
			reMap.put("msg", "店铺信息为空");
			return reMap;
		}
	}
	
	/**
	 * inputStream转换成file
	 * 用于spring前端传来的commonsmultipartfile转file
	 * @param is
	 * @param file
	 */
	private static void iStreamToFile(InputStream is,File file){
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int byteRead = 0;
			byte[] byteBuff = new byte[2014];
			while((byteRead = is.read(byteBuff)) != -1){
				os.write(byteBuff, 0, byteRead);
			}
		} catch (Exception e) {
			throw new RuntimeException("调用inputStream转换file方法异常："+e.getMessage());
		}finally {
			try{
				if(is!=null){
					is.close();
				}
				if(os!=null){
					os.close();
				}
			}catch(IOException e){
				throw new RuntimeException("IO关闭异常："+e.getMessage());
			}
		}
	}
}
