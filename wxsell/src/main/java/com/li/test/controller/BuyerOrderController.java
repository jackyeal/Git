package com.li.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.li.test.VO.ResultVO;
import com.li.test.converter.OrderForm2OrderDTOConverter;
import com.li.test.dto.OrderDTO;
import com.li.test.enums.ResultEnum;
import com.li.test.exception.SellException;
import com.li.test.form.OrderForm;
import com.li.test.service.BuyerService;
import com.li.test.service.OrderService;
import com.li.test.utils.ResultVOUtil;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.Return;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

	@Autowired
	private OrderService orderservice;
	
	@Autowired
	private BuyerService buyerService;
	
	//创建订单
	@PostMapping("/create")
	public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
			BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【创建订单】参数不正确，orderform={}",orderForm);
			throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		OrderDTO orderDTO=OrderForm2OrderDTOConverter.convert(orderForm);
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailsList())) {
			log.error("【创建订单】购物车不能为空");
			throw new SellException(ResultEnum.CART_EMPTY);
		}
		OrderDTO createResult=orderservice.oncreate(orderDTO);
		Map<String, String> map=new HashMap<>();
		map.put("orderId",createResult.getOrderId());
		
		return ResultVOUtil.success(map);
	}
	
	//订单列表
	@GetMapping("/list")
	public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
			                             @RequestParam(value = "page",defaultValue = "0") Integer page,
			                             @RequestParam(value = "size",defaultValue = "10") Integer size){
		if(StringUtils.isEmpty(openid)) {
			log.error("【查询订单列表】openid为空");
			throw new SellException(ResultEnum.PARAM_ERROR);
		}
		Page<OrderDTO> orderDTOPage=orderservice.findList(openid, PageRequest.of(page, size));
		return ResultVOUtil.success(orderDTOPage);
	}
	//订单详情
	@GetMapping("/detail")
	public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
			                         @RequestParam("orderId") String orderId){
		
		OrderDTO orderDTO=buyerService.findOrderOne(openid, orderId);
		return ResultVOUtil.success(orderDTO);
	}
	//取消订单
	@PostMapping("/cancel")
	public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
		buyerService.findOrderOne(openid, orderId);
		return ResultVOUtil.success();
	}
}
