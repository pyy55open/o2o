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

import com.csy.o2o.dto.ProductExcution;
import com.csy.o2o.entity.Product;
import com.csy.o2o.entity.ProductCategory;
import com.csy.o2o.entity.Shop;
import com.csy.o2o.entity.ShopCategory;
import com.csy.o2o.service.ProductCategoryService;
import com.csy.o2o.service.ProductService;
import com.csy.o2o.service.ShopService;
import com.csy.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/front")
public class ShopDetailController {
	
	@Autowired
	ShopService shopService;
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	@Autowired
	ProductService productService;
	
	/**
	 * 获取店铺详细信息，店铺商品类别
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getshopdetail",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getShopDetail(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		//页面传来的店铺ID
		Long shopID = HttpServletRequestUtil.getLong(request, "shopID");
		if(shopID>-1){
			Shop shop = shopService.getByShopId(shopID);
			List<ProductCategory> pcList = productCategoryService.getProductCategoryList(shopID);
			reMap.put("shop", shop);
			reMap.put("pcList", pcList);
			reMap.put("success", true);
		}else{
			reMap.put("success", false);
			reMap.put("msg", "店铺ID为空。");
		}
		return reMap;
	}
	/**
	 * 获取店铺商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getshopproduct",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getShopProduct(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		Long shopID = HttpServletRequestUtil.getLong(request, "shopID");
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Product product = null;
		if(shopID >-1 && pageIndex >-1 && pageSize >-1){
			Long productCategoryID = HttpServletRequestUtil.getLong(request, "productCategoryID");
			String productCategoryName = HttpServletRequestUtil.getString(request, "productCategoryName");
			product = setProductCondition(productCategoryID,productCategoryName);
			Shop shop = new Shop();
			shop.setShopid(shopID);
			product.setShop(shop);
			ProductExcution productExcution = productService.queryProductOfShop(product, pageIndex, pageSize);
			reMap.put("productList", productExcution.getProductlist());
			reMap.put("count", productExcution.getCount());
			reMap.put("success", true);
		}else{
			reMap.put("success", false);
			reMap.put("msg", "店铺ID、分页标签，分页大小为空。");
		}
		return reMap;
	}


	private Product setProductCondition(Long productCategoryID, String productCategoryName) {
		Product product = new Product();
		ProductCategory productCategory = new ProductCategory();
		if(productCategoryID > -1){
			productCategory.setProductCategoryid(productCategoryID);
		}
		if(productCategoryName!=null){
			productCategory.setProductCategoryname(productCategoryName);
		}
		product.setProductCategory(productCategory);
		product.setEnableStatus(1);
		return product;
	}
}
