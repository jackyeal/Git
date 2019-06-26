package com.li.test.converter;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.li.test.dto.OrderDTO;
import com.li.test.entities.OrderDetail;
import com.li.test.enums.ResultEnum;
import com.li.test.exception.SellException;
import com.li.test.form.OrderForm;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class OrderForm2OrderDTOConverter {

	public static OrderDTO convert(OrderForm orderForm) {
		Gson gson=new Gson();
		
		OrderDTO orderDTO=new OrderDTO();
		
		orderDTO.setBuyerName(orderForm.getName());
		orderDTO.setBuyerPhone(orderForm.getPhone());
		orderDTO.setBuyerAddress(orderForm.getAddress());
		orderDTO.setBuyerOpenid(orderForm.getOpenid());
		
		List<OrderDetail> orderDetail=new ArrayList<>();
		try {
			orderDetail=gson.fromJson(orderForm.getItems(), 
					new TypeToken<List<OrderDetail>>() {}.getType());
		} catch (Exception e) {
			log.error("【对象转换】错误，String={}",orderForm.getItems());
			throw new SellException(ResultEnum.PARAM_ERROR);
		}
		
		orderDTO.setOrderDetailsList(orderDetail);
		
		return orderDTO;
	}
}
