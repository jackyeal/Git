package com.li.test.service.serviceImpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.li.test.dto.OrderDTO;
import com.li.test.enums.ResultEnum;
import com.li.test.exception.SellException;
import com.li.test.service.OrderService;
import com.li.test.service.PayService;
import com.li.test.utils.JsonUtil;
import com.li.test.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

	private static final String ORDER_NAME = "微信购买订单";
	@Autowired
	private BestPayServiceImpl bestPayService;

	@Autowired
	private OrderService orderService;

	@Override
	public PayResponse create(OrderDTO orderDTO) {
		PayRequest payRequest = new PayRequest();
		payRequest.setOpenid(orderDTO.getBuyerOpenid());
		payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
		payRequest.setOrderId(orderDTO.getOrderId());
		payRequest.setOrderName(ORDER_NAME);
		payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
		log.info("【微信支付】 发起支付，request={}", JsonUtil.toJson(payRequest));

		PayResponse payResponse = bestPayService.pay(payRequest);
		log.info("【微信支付】 发起支付，response ={}", JsonUtil.toJson(payResponse));
		return payResponse;
	}

	@Override
	public PayResponse notify(String notifyData) {
		// TODO Auto-generated method stub
		PayResponse payResponse = bestPayService.asyncNotify(notifyData);
		log.info("【微信支付】异步通知，payResponse={}", payResponse);

		// 修改订单支付状态
		OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());

		// 判断订单是否存在
		if (orderDTO == null) {
			log.error("【微信支付】异步通知，订单不存在，orderId={}", payResponse.getOrderId());
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}

		// 判断金额是否一致
		if (!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())) {
			log.error("【微信支付】 异步通知，订单金额不一致，orderId={}，微信通知金额={},系统金额={}", payResponse.getOrderId(),
					payResponse.getOrderAmount(), orderDTO.getOrderAmount());
			throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
		}
		orderService.paid(orderDTO);
		return payResponse;
	}

	@Override
	public RefundResponse refund(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		RefundRequest refundRequest=new RefundRequest();
		refundRequest.setOrderId(orderDTO.getOrderId());
		refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
		refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
		log.info("【微信退款】request={}",refundRequest);
		
		RefundResponse refundResponse=bestPayService.refund(refundRequest);
		log.info("【微信退款】response={}",refundResponse);
		return refundResponse;
		
	}
}
