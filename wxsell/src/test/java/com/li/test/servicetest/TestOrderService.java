package com.li.test.servicetest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.li.test.dto.OrderDTO;
import com.li.test.entities.OrderDetail;
import com.li.test.enums.OrderStatusEnum;
import com.li.test.enums.PayStatusEnum;
import com.li.test.service.serviceImpl.OrderServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestOrderService {

	@Autowired
	private OrderServiceImpl orderService;

	private final String BUYER_OPENID = "110124";

	private final String ORDER_ID = "1557124452153483945";

//	@Test
//	public void create() throws Exception {
//		OrderDTO orderDTO = new OrderDTO();
//		orderDTO.setBuyerName("jackyeal");
//		orderDTO.setBuyerOpenid(BUYER_OPENID);
//		orderDTO.setBuyerPhone("1234567890");
//		orderDTO.setBuyerAddress("广东广州");
//
//		// 购物车
//		List<OrderDetail> orderdetaiList = new ArrayList<>();
//
//		OrderDetail orderDetail = new OrderDetail();
//		orderDetail.setProductId("121");
//		orderDetail.setProductQuantity(1);
//		orderDetail.setProductIcon("http://dsadsaf.jpg");
//		orderdetaiList.add(orderDetail);
//
//		orderDTO.setOrderDetailsList(orderdetaiList);
//
//		OrderDTO result = orderService.oncreate(orderDTO);
//		log.info("【创建订单】 result={}", result);
//		Assert.assertNotNull(result);
//	}
//
	@Test
	public void findOne() {
		OrderDTO resultDto = orderService.findOne(ORDER_ID);
		log.info("查询订单 result={}", resultDto);
		Assert.assertEquals(ORDER_ID, resultDto.getOrderId());
	}
//
//	@Test
//	public void findList() {
//		Page<OrderDTO> orderPage = orderService.findList(BUYER_OPENID, PageRequest.of(0, 1));
//		Assert.assertNotEquals(0, orderPage.getTotalElements());
//	}
	@Test
	public void cancal() {
		OrderDTO orderDTO=orderService.findOne(ORDER_ID);
		OrderDTO result=orderService.cancel(orderDTO);
		Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
	}
	@Test
	public void finished() {
		OrderDTO orderDTO=orderService.findOne(ORDER_ID);
		OrderDTO result=orderService.finished(orderDTO);
		Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
	}
	
	@Test
	public void paid() {
		OrderDTO orderDTO=orderService.findOne(ORDER_ID);
		OrderDTO result=orderService.paid(orderDTO);
		Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
	}
	
	@Test
	public void list() {
		Page<OrderDTO> orderDtoPage=orderService.findList(PageRequest.of(0, 2));
		Assert.assertNotEquals(0, orderDtoPage.getTotalElements());
	}
	
	
	
	
	
	
	
	
	
	
}
