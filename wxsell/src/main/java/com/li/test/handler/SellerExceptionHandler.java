package com.li.test.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.li.test.exception.SellerAuthorizeException;

@ControllerAdvice
public class SellerExceptionHandler {

	//拦截登录异常
	@ExceptionHandler(value = SellerAuthorizeException.class)
	public ModelAndView handlerAuthorizeException() {
		return new ModelAndView("redirect:"
				.concat("http://localhost:8080"));
	}
	
}
