package com.li.test.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.li.test.dto.OrderDTO;
import com.li.test.entities.OrderMaster;

public interface OrderService {

	//创建订单
	OrderDTO oncreate(OrderDTO orderDTO);
	
	//查询单个订单
	OrderDTO findOne(String orderId);
	
	//查询订单列表
	Page<OrderDTO> findList(String buyerOpenid,Pageable pageable);
	
	//取消订单
	OrderDTO cancel(OrderDTO orderDTO);
	
	//完结订单
	OrderDTO finished(OrderDTO orderDTO);
	
	//支付订单
	OrderDTO paid(OrderDTO orderDTO);
	
	//查询订单列表
    Page<OrderDTO> findList(Pageable pageable);
		
}
