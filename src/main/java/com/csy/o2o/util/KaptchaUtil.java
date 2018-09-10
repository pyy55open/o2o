package com.csy.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class KaptchaUtil {

	public static boolean checkKaptcha(HttpServletRequest request){
		String kaptcha = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String kaptchaActual = HttpServletRequestUtil.getString(request, "kaptcha");
		if(kaptcha==null || !kaptcha.equals(kaptchaActual)){
			return false;
		}
		return true;
		
	}
}
