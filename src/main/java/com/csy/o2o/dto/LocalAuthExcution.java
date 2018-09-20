package com.csy.o2o.dto;

import java.util.List;

import com.csy.o2o.entity.LocalAuth;
import com.csy.o2o.enu.LocalAuthEnum;

public class LocalAuthExcution {
	
		//状态
		private int state;
		
		//状态标识
		private String stateInfo;
		
		//数量
		private int count;
		
		private	LocalAuth localAuth;
		
		private List<LocalAuth> list;
		
		public LocalAuthExcution() {
		}
		
		public LocalAuthExcution(LocalAuthEnum localAuthEnum) {
			this.state = localAuthEnum.getState();
			this.stateInfo = localAuthEnum.getStateInfo();
		}
		
		public LocalAuthExcution(LocalAuthEnum localAuthEnum,List<LocalAuth> list) {
			this.state = localAuthEnum.getState();
			this.stateInfo = localAuthEnum.getStateInfo();
			this.list = list;
		}
		
		public LocalAuthExcution(LocalAuthEnum localAuthEnum,LocalAuth localAuth) {
			this.state = localAuthEnum.getState();
			this.stateInfo = localAuthEnum.getStateInfo();
			this.localAuth = localAuth;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public String getStateInfo() {
			return stateInfo;
		}

		public void setStateInfo(String stateInfo) {
			this.stateInfo = stateInfo;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public LocalAuth getLocalAuth() {
			return localAuth;
		}

		public void setLocalAuth(LocalAuth localAuth) {
			this.localAuth = localAuth;
		}

		public List<LocalAuth> getList() {
			return list;
		}

		public void setList(List<LocalAuth> list) {
			this.list = list;
		}
		
}
