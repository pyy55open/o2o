package com.csy.o2o.interceptor.shopadmin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.csy.o2o.entity.PersonInfo;

public class ShopLoginInterceptor extends HandlerInterceptorAdapter{

	/**
	 * 拦截器，判断用户是否是登录状态
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		if(user!=null){
			if(user.getUserid()!=null&&user.getEnablestatus()==1&&user.getUsertype()==2){
				return true;
			}
		}
		PrintWriter pw = response.getWriter();
		pw.println("<html>");
		pw.println("<script>");
		pw.println("window.open ('" + request.getContextPath() + "/local/loginpage?usertype=2','_self')");
		pw.println("</script>");
		pw.println("</html>");
		return false;
	}
}
