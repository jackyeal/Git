package com.li.test.service;

import com.li.test.entities.UserLogin;

public interface UserService {

	//登录验证
	boolean login(UserLogin userLogin);
	
	//注册新账号
	boolean logon(UserLogin userLogin);
}
