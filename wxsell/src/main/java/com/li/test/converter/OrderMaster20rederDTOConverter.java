package com.li.test.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.li.test.dto.OrderDTO;
import com.li.test.entities.OrderMaster;

public class OrderMaster20rederDTOConverter {

	public static OrderDTO convert(OrderMaster orderMaster) {
		OrderDTO orderDTO=new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		return orderDTO;
	}
	public static List<OrderDTO> convert(List<OrderMaster> orderMasters){
		return orderMasters.stream().map(e->convert(e)
				).collect(Collectors.toList());
	}
}
