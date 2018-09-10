package com.csy.o2o.web.shopadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csy.o2o.dto.ProductCategoryExcution;
import com.csy.o2o.dto.Result;
import com.csy.o2o.entity.ProductCategory;
import com.csy.o2o.entity.Shop;
import com.csy.o2o.enu.ProductCategoryStateEnum;
import com.csy.o2o.service.ProductCategoryService;

@Controller
@RequestMapping("shopadmin")
public class ProductCategoryManagementController {

	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		if (currentShop != null && currentShop.getShopid() > 0) {
			list = productCategoryService.getProductCategoryList(currentShop.getShopid());
			return new Result<List<ProductCategory>>(true, list);
		} else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
		}
	}
	
	@RequestMapping(value = "/addproductcategorybatch", method = RequestMethod.POST)
	public Map<String,Object> addProductCategoryBatch(@RequestBody List<ProductCategory> pcList,HttpServletRequest request){
		Map<String,Object> reMap = new HashMap<String,Object>();
		Shop shop = (Shop) request.getSession().getAttribute("currentShop");
		for (ProductCategory productCategory : pcList) {
			productCategory.setShopid(shop.getShopid());
			productCategory.setCreateTime(new Date());
		}
		if(pcList!=null&&pcList.size()>0){
			try{
				ProductCategoryExcution pe = productCategoryService.batchInsertProductCategory(pcList);
				if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
					reMap.put("success", true);
					reMap.put("msg", "添加成功。");
				}else{
					reMap.put("success", false);
					reMap.put("msg", pe.getStateInfo());
				}
			}catch(Exception e){
				reMap.put("success", false);
				reMap.put("msg", "error :"+e.getMessage());
			}
			
		}else{
			reMap.put("success", false);
			reMap.put("msg", "请填写商品类别信息。");
		}
		return reMap;
	}
	
	@RequestMapping(value="/deleteproductcategory",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteProductCategory(long productCategoryID,HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		try{
			Shop shop = (Shop) request.getSession().getAttribute("currentShop");
			ProductCategoryExcution pce = productCategoryService.deleteProductCategory(productCategoryID, shop.getShopid());
			if(pce.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
				reMap.put("success", true);
				reMap.put("msg", "删除成功");
			}else{
				reMap.put("success", false);
				reMap.put("msg", ProductCategoryStateEnum.INNER_ERROR.getStateInfo());
			}
		}catch(Exception e){
			reMap.put("success", false);
			reMap.put("msg", "errpr:"+e.getMessage());
		}
		return reMap;
	}
}
