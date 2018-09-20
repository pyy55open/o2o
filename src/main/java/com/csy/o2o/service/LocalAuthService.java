package com.csy.o2o.service;

import java.util.Date;

import com.csy.o2o.dto.LocalAuthExcution;
import com.csy.o2o.entity.LocalAuth;

public interface LocalAuthService {

	/**
	 * 登录校验账号密码并获取账号信息
	 * @param userid
	 * @param name
	 * @param password
	 */
	public LocalAuth loginByNamePwd(String name,String password);
	
	/**
	 * 根据用户id获取账号信息
	 * @param userid
	 * @return
	 */
	public LocalAuth getLocalAuthByID(Long userid);
	
	/**
	 * 
	 * @param userid
	 * @param name
	 * @param password
	 * @param newPassword
	 * @param now
	 * @return
	 */
	public LocalAuthExcution updatePwd(Long userid,String name,String password,String newPassword,Date now);
}
