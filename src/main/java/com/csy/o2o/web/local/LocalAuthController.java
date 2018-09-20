package com.csy.o2o.web.local;

import java.util.Date;
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

import com.csy.o2o.dto.LocalAuthExcution;
import com.csy.o2o.entity.LocalAuth;
import com.csy.o2o.enu.LocalAuthEnum;
import com.csy.o2o.service.LocalAuthService;
import com.csy.o2o.util.HttpServletRequestUtil;
import com.csy.o2o.util.KaptchaUtil;

@Controller
@RequestMapping(value="/local",method=RequestMethod.GET)
public class LocalAuthController {

	@Autowired
	LocalAuthService localAuthService;
	
	Logger log = LoggerFactory.getLogger(LocalAuthController.class);
	
	/**
	 * 登录校验
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		//密码输错三次才需要验证码
		boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
		if(needVerify && !KaptchaUtil.checkKaptcha(request)){
			reMap.put("success", false);
			reMap.put("msg", "验证码输入有误");
			return reMap;
		}
		//获取输入的账号密码
		String name = HttpServletRequestUtil.getString(request, "name");
		String password = HttpServletRequestUtil.getString(request, "password");
		log.debug("==========输入的账号:"+name+"  输入的密码:"+password);
		if(name!=null&&password!=null){
			LocalAuth localAuth = localAuthService.loginByNamePwd(name, password);
			if(localAuth!=null){
				reMap.put("success", true);
				request.getSession().setAttribute("user", localAuth.getPersonInfo());
				return reMap;
			}else{
				reMap.put("success", false);
				reMap.put("msg", "账号密码错误。");
				return reMap;
			}
		}else{
			reMap.put("success", false);
			reMap.put("msg", "请输入账号密码。");
			return reMap;
		}
	}
	@RequestMapping(value="/updatePassword",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePassword(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		Long userid = HttpServletRequestUtil.getLong(request, "userID");
		String name = HttpServletRequestUtil.getString(request, "name");
		String password = HttpServletRequestUtil.getString(request, "password");
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		log.info("==========输入的账号:"+name+"   输入的密码:"+password+"  输入的新密码:"+newPassword);
		if(name!=null&&name!=null&&password!=null&&newPassword!=null){
			try{
				LocalAuthExcution localAuthExcution = localAuthService.updatePwd(userid, name, password, newPassword, new Date());
				if(localAuthExcution.getState()==LocalAuthEnum.SUCCESS.getState()){
					reMap.put("success", true);
					reMap.put("msg", localAuthExcution.getStateInfo());
				}else{
					reMap.put("success", false);
					reMap.put("msg", localAuthExcution.getStateInfo());
				}
			}catch(Exception e){
				reMap.put("success", false);
				reMap.put("msg", "密码修改异常:"+e.getMessage());
				log.error("密码修改异常:"+e.getMessage());
			}
		}else{
			reMap.put("success", false);
			reMap.put("msg", "请输入账号、密码、新密码");
		}
		return reMap;
	}
	//登出
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request){
		Map<String, Object> reMap = new HashMap<String,Object>();
		request.getSession().setAttribute("user", null);
		reMap.put("success", true);
		return reMap;
	}
}
