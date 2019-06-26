package com.li.test.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.li.test.enums.ResultEnum;
import com.li.test.exception.SellException;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts.OAuth2Scope;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

	@Autowired WxMpService wxMpService;
	
	@GetMapping("/authorize")
	public String authorize(@RequestParam("returnUrl")String returnUrl) throws UnsupportedEncodingException {
		String url="http://localhost:8080/wxsell/wechat/userInfo";
		String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(url, OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl, "UTF-8"));
		log.info("【微信网页授权】获取code，result={}",redirectUrl);
		return "redirect:"+redirectUrl;
	}
	
	@GetMapping("/userInfo")
	public String userInfo(@RequestParam("code") String code,
			             @RequestParam("state")String returnUrl) {
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken=new WxMpOAuth2AccessToken();
		try {
			wxMpService.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			log.error("【微信网页授权】{}",e);
			throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
		}
            String openId=wxMpOAuth2AccessToken.getOpenId();
            return "redirect:"+returnUrl+"?openid="+openId;
	}
	
	
	
}
