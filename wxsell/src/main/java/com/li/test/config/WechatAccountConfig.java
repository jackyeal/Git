package com.li.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

	private String mpAppId;
	
	private String mpAppSecret;
	
	/*
	 * 商户号
	 */
	private String mchId;
	
	/*
	 * 商户密钥
	 */
	private String mchKey;
	
	/*
	 * 商户证书路径
	 */
	private String keyPath;
	
	/*
	 * 微信异步通知地址
	 */
	private String notifyUrl;
}
