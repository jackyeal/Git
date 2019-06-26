package com.li.test.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.li.test.dto.OrderDTO;
import com.li.test.enums.ResultEnum;
import com.li.test.exception.SellException;
import com.li.test.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

	@Autowired
	private OrderService orderservice;

	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
		Page<OrderDTO> orderDTOPage = orderservice.findList(PageRequest.of(page - 1, size));
		map.put("orderDTOPage", orderDTOPage);
		map.put("currentPage", page);
		map.put("size", size);
		// orderDTOPage.getTotalPages();
		return new ModelAndView("order/list", map);
	}
//商家取消订单
	@GetMapping("/cancel")
	public ModelAndView cancel(@RequestParam("orderId") String orderId, Map<String, Object> map) {
		try {
			OrderDTO orderDTO = orderservice.findOne(orderId);
			orderservice.cancel(orderDTO);
			
		} catch (SellException e) {
			// TODO: handle exception

			log.error("【卖家端取消订单】 查询不到订单");

			map.put("msg", e.getMessage());
			map.put("url", "/seller/order/list");
			return new ModelAndView("common/error", map);
		}

		map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
		map.put("url", "/seller/order/list");		
		return new ModelAndView("common/success");
	}
	//订单详情
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("orderId") String orderId,
			                   Map<String, Object> map) {
		OrderDTO orderDTO=new OrderDTO();
		try {
			orderDTO=orderservice.findOne(orderId);

		} catch (SellException e) {
			// TODO: handle exception
			log.error("【卖家端订单详情】 发生异常{}",e);
			map.put("msg", e.getMessage());
			map.put("url", "/seller/order/list");
			return new ModelAndView("common/error", map);
		}
		
		map.put("orderDTO",orderDTO);
		return new ModelAndView("order/detail",map);
	}
	
	//商家完结订单
	@GetMapping("/finsih")
	public ModelAndView finished(@RequestParam("orderId") String orderId,
			                     Map<String, Object> map) {
		OrderDTO orderDTO=new OrderDTO();
	
		try {
			orderDTO=orderservice.findOne(orderId);
            orderservice.finished(orderDTO);
		} catch (SellException e) {
			log.error("【卖家端订单完结】 发生异常{}",e);
			map.put("msg", e.getMessage());
			map.put("url", "/seller/order/list");
			return new ModelAndView("common/error", map);
	}
		map.put("msg", ResultEnum.ORDER_FINISHED_SUCCESS.getMessage());
		map.put("url", "/seller/order/list");		
		return new ModelAndView("common/success");
		
		}
}
