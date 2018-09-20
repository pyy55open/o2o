package com.csy.o2o.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.csy.o2o.entity.LocalAuth;
import com.sun.tracing.dtrace.ProviderAttributes;

public interface LocalAuthDao {

	/**
	 * 通过账号密码登录
	 * @param name
	 * @param password
	 * @return
	 */
	LocalAuth loginByNamePasswd(@Param("name")String name,@Param("password")String password);
	
	/**
	 * 通过ID查询用户信息
	 * @param userID
	 * @return
	 */
	LocalAuth queryByID(@Param("userID")Long userID);
	
	/**
	 * 注册用户
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);
	
	/**
	 * 修改密码
	 * @param userID
	 * @param name
	 * @param password
	 * @param newPassword
	 * @return
	 */
	int updatePasswd(@Param("userID")Long userID,@Param("name")String name
			,@Param("password")String password,@Param("newPassword")String newPassword,@Param("updateTime")Date updateTime);
}
