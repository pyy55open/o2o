package com.csy.o2o.interceptor.shopadmin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.csy.o2o.entity.Shop;

public class ShopPermissionInterceptor extends HandlerInterceptorAdapter{

	/**
	 * 拦截器，控制器之前执行，判断用户是否是店铺的管理员
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Shop shop = (Shop) request.getSession().getAttribute("currentShop");
		@SuppressWarnings("unchecked")
		List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
		if(shop!=null && shopList!=null){
			for (Shop s : shopList) {
				if(s.getShopid() == shop.getShopid()){
					return true;
				}
			}
		}
		return false;
	}
}
