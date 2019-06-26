package com.li.test.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.li.test.entities.UserLogin;
import com.li.test.enums.ResultEnum;
import com.li.test.exception.SellException;
import com.li.test.repository.UserLoginRepository;
import com.li.test.service.UserService;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserLoginRepository userRepository;
	
	@Override
	public boolean login(UserLogin userLogin) {
		// TODO Auto-generated method stub
		Optional<UserLogin> userLogin2=userRepository.findById(userLogin.getAccountName());
		if (userLogin2.isPresent()) {
			UserLogin userLogin3=userLogin2.get();
			
			if (userLogin.getPassword().equals(userLogin3.getPassword())) {
				return true;
			}
			log.error("【登录账号】账号或密码不正确,accountName={}",userLogin.getAccountName());
			throw new SellException(ResultEnum.ACCOUNT_ERROE);
		}
		else {
			log.error("【登录账号】账号或密码不正确,accountName={}",userLogin.getAccountName());
			throw new SellException(ResultEnum.ACCOUNT_ERROE);

		}

	}

	@Override
	public boolean logon(UserLogin userLogin) {
		// TODO Auto-generated method stub
		if (userLogin.getAccountName().length()!=11) {
			throw new SellException(ResultEnum.ACCOUNT_FORMAT_ERROR);
		}
		Optional<UserLogin> userLogin2=userRepository.findById(userLogin.getAccountName());
		if (!userLogin2.isPresent()) {
			log.error("【注册账号】账号已存在，accountName={}",userLogin.getAccountName());
			throw new SellException(ResultEnum.ACCOUNT_EXIST_ERROR);
		}
		
		UserLogin userLogin3=new UserLogin();
		userLogin3.setAccountName(userLogin.getAccountName());
		userLogin3.setPassword(userLogin.getPassword());
		userRepository.save(userLogin3);
		return false;
	}

}
