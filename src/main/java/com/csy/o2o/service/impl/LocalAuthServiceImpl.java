package com.csy.o2o.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csy.o2o.dao.LocalAuthDao;
import com.csy.o2o.dto.LocalAuthExcution;
import com.csy.o2o.entity.LocalAuth;
import com.csy.o2o.enu.LocalAuthEnum;
import com.csy.o2o.exception.LocalAuthOperationException;
import com.csy.o2o.service.LocalAuthService;
@Service
public class LocalAuthServiceImpl implements LocalAuthService{
	
	@Autowired
	LocalAuthDao localAuthDao;
	
	Logger log = LoggerFactory.getLogger(LocalAuthServiceImpl.class);
	
	@Override
	public LocalAuth loginByNamePwd(String name, String password) {
		return localAuthDao.loginByNamePasswd(name, password);
	}

	@Override
	public LocalAuth getLocalAuthByID(Long userid) {
		return localAuthDao.queryByID(userid);
	}

	@Override
	public LocalAuthExcution updatePwd(Long userid, String name, String password, String newPassword, Date now) {
		if(userid!=null&&name!=null&&password!=null&&newPassword!=null&&!password.equals(newPassword)){
			try{
				int i = localAuthDao.updatePasswd(userid, name, password, newPassword,now);
				if(i < 0){
					throw new LocalAuthOperationException(LocalAuthEnum.INNER_ERROR.getStateInfo());
				}
				return new LocalAuthExcution(LocalAuthEnum.SUCCESS);
			}catch(Exception e){
				log.error(e.getMessage());
				throw new LocalAuthOperationException(LocalAuthEnum.INNER_ERROR.getStateInfo());
			}
		}else{
			throw new LocalAuthOperationException(LocalAuthEnum.INNER_ERROR.getStateInfo());
		}
	}

}
