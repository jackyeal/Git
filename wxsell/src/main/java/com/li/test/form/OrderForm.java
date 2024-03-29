package com.li.test.form;



import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class OrderForm {

	/*
	 * 买家姓名
	 */
	@NotEmpty(message = "姓名必填")
	private String name;
	
	/*
	 * 买家手机号
	 */
	@NotEmpty(message = "手机号必填")
	private String phone;
	
	/*
	 * 买家地址
	 */
	@NotEmpty(message = "地址必填")
	private String address;
	
	/*
	 * 买家微信openid
	 */
	private String openid;
	
	@NotEmpty(message = "购物车不能为空")
	private String items;
}
