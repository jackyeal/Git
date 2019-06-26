package com.li.test.aspect;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.li.test.constant.CookieConstant;
import com.li.test.constant.Redisconstant;
import com.li.test.exception.SellerAuthorizeException;
import com.li.test.utils.CookieUtil;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

	@Autowired
	private StringRedisTemplate redis;
	
	@Pointcut("execution(public * com.li.test.controller.Seller*.*(..))")
	public void verify() {}
	
	@Before("verify()")
	public void doVerify() {
		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		
		//查询Cookie
		Cookie cookie=CookieUtil.get(request, CookieConstant.TOKEN);
		if (cookie==null) {
			log.warn("【登录校验】 Cookie中查不到token");
			throw new SellerAuthorizeException();
		}
		
		//redis中查询
		String tokenValue=redis.opsForValue().get(String.format(Redisconstant.TOKEN_PREFIX, cookie.getValue()));
		if (StringUtils.isEmpty(tokenValue)) {
			log.warn("【登录校验】 Cookie中查不到token");
			throw new SellerAuthorizeException();
		}
	}
	
	
	
	
}
