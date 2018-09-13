package com.csy.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csy.o2o.dto.ShopException;
import com.csy.o2o.entity.Area;
import com.csy.o2o.entity.Shop;
import com.csy.o2o.entity.ShopCategory;
import com.csy.o2o.service.AreaService;
import com.csy.o2o.service.ShopCategoryService;
import com.csy.o2o.service.ShopService;
import com.csy.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/front")
public class ShopListController {

	@Autowired
	ShopService shopService;
	
	@Autowired
	ShopCategoryService shopCategoryService;
	
	@Autowired
	AreaService areaService;
	
	@RequestMapping(value="/getshopList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopList(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		Long parentID = HttpServletRequestUtil.getLong(request, "parentID");
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		ShopCategory shopCategory = new ShopCategory();
		ShopCategory son = new ShopCategory();
		Shop shop = new Shop();
		if(pageIndex>-1&&pageSize>-1){
			//获取页面传来的地域ID
			int areaID = HttpServletRequestUtil.getInt(request, "areaID");
			if(areaID!=-1L){
				Area area = new Area();
				area.setAreaid(areaID);
				shop.setArea(area);
			}
			//获取页面传来的店铺类别ID
			Long shopCategoryID = HttpServletRequestUtil.getLong(request, "shopCategoryID");
			if(shopCategoryID!=-1L){
				son.setShopCategoryid(shopCategoryID);
			}
			//获取页面传来的店铺名
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			if(parentID > -1){
				shopCategory.setShopCategoryid(parentID);
				son.setParent(shopCategory);
				shop.setShopCategory(son);
				shop.setShopname(shopName);
				ShopException shopException = shopService.getShopList(shop, pageIndex, pageSize);
				List<ShopCategory> scList = shopCategoryService.queryShopCategory(son);
				List<Shop> shopList = shopException.getShoplist();
				reMap.put("success", true);
				reMap.put("shopList", shopList);
				reMap.put("scList", scList);
				reMap.put("count", shopException.getCount());
			}else{
				son.setParent(null);
				shop.setShopCategory(son);
				ShopException shopException = shopService.getShopList(shop, pageIndex, pageSize);
				List<ShopCategory> scList = shopCategoryService.queryShopCategory(son);
				List<Shop> shopList = shopException.getShoplist();
				reMap.put("success", true);
				reMap.put("shopList", shopList);
				reMap.put("scList", scList);
				reMap.put("count", shopException.getCount());
			}
			List<Area> areaList = areaService.getArea();
			reMap.put("areaList", areaList);
		}else{
			reMap.put("success", false);
		}
		return reMap;
	}
}
