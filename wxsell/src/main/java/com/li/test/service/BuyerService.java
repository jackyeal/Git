package com.li.test.service;

import com.li.test.dto.OrderDTO;

public interface BuyerService {

	//查询一个订单
	OrderDTO findOrderOne(String openid,String orderId);
	
	//取消订单
	OrderDTO cancaelOrder(String openid,String prderId);
	
}
