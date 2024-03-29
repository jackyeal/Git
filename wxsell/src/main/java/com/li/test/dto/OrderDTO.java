package com.li.test.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.li.test.entities.OrderDetail;
import com.li.test.enums.OrderStatusEnum;
import com.li.test.enums.PayStatusEnum;
import com.li.test.utils.EnumUtil;
import com.li.test.utils.serializer.Date2LongSerializer;

import lombok.Data;
@Data 
public class OrderDTO {

	/* 订单id */
	private String orderId;

	/* 买家姓名 */
	private String buyerName;

	/* 买家手机号 */
	private String buyerPhone;

	/* 买家地址 */
	private String buyerAddress;

	/* 买家微信Openid */
	private String buyerOpenid;

	/* 订单总金额 */
	private BigDecimal orderAmount;

	/* 订单状态，默认新下单 */
	private Integer orderStatus;

	/* 支付状态，默认未支付 */
	private Integer payStatus;
	
	private OrderStatusEnum orderStatusEnum;
	
	private PayStatusEnum payEnum;

	/* 创建时间 */
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTime;

	/* 更新时间 */
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTime;

	List<OrderDetail> orderDetailsList;

	@JsonIgnore
	public OrderStatusEnum getOrderStatusEnum() {
		return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
	}
	
	@JsonIgnore
	public PayStatusEnum getpayEnum() {
		return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
	}
}
