package com.li.test.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.li.test.constant.CookieConstant;
import com.li.test.constant.Redisconstant;
import com.li.test.entities.UserLogin;
import com.li.test.enums.ResultEnum;
import com.li.test.exception.SellException;
import com.li.test.form.Userform;
import com.li.test.service.UserService;
import com.li.test.utils.CookieUtil;

@Controller
@RequestMapping("/user")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private StringRedisTemplate redis;

	@PostMapping("/login")
	public ModelAndView login(@Valid Userform form, BindingResult bindingResult, HttpServletResponse response,
			Map<String, Object> map) {
		if (bindingResult.hasErrors()) {
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/");
			return new ModelAndView("common/error", map);
		}

		UserLogin userLogin = new UserLogin();
		BeanUtils.copyProperties(form, userLogin);
		if (!(StringUtils.isEmpty(form.getAccountName()) || StringUtils.isEmpty(form.getPassword()))) {
			try {
				userService.login(userLogin);
			} catch (SellException e) {
				// TODO: handle exception
				map.put("msg",e.getMessage());
				map.put("url", "/");
				return new ModelAndView("common/error", map);
			}
			String token = UUID.randomUUID().toString();
			Integer expire = Redisconstant.EXPIRE;
			redis.opsForValue().set(String.format(Redisconstant.TOKEN_PREFIX, token), form.getAccountName(), expire,
					TimeUnit.SECONDS);

			CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
			map.put("url", "/seller/order/list");
			return new ModelAndView("common/success", map);

	}else
	{
		map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
		map.put("url", "/");
		return new ModelAndView("common/error", map);

	}}
	

	@RequestMapping("/logon")
	public boolean logon(UserLogin userLogin) {
		return userService.logon(userLogin);
	}

	@GetMapping("/logout")
	public void logout() {
	}

}
