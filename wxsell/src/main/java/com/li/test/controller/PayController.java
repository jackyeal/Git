package com.li.test.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.li.test.dto.OrderDTO;
import com.li.test.enums.ResultEnum;
import com.li.test.exception.SellException;
import com.li.test.service.OrderService;
import com.li.test.service.PayService;
import com.lly835.bestpay.model.PayResponse;

@Controller
@RequestMapping("/pay")
public class PayController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PayService payService; 
	
	@GetMapping("/create")
	public ModelAndView create(@RequestParam("orderId") String orderId,
			@RequestParam("returnUrl") String returnUrl,
			Map<String,Object> map) {
		//查询订单
		OrderDTO orderDTO=orderService.findOne(orderId);
		if (orderDTO==null) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		
		//发起支付
		PayResponse payResponse=payService.create(orderDTO);
		
		map.put("payResponse", payResponse);
		map.put("returnUrl", returnUrl);
//		
		return new ModelAndView("pay/create",map);
		
	}
	@PostMapping("/notify")
	public ModelAndView notify(@RequestBody String notifyData) {
		PayResponse payResponse=payService.notify(notifyData);
		
		//返回给微信处理结果
		return new ModelAndView("pay/success");
	}
}
