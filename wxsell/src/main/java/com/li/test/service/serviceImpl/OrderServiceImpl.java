package com.li.test.service.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.li.test.converter.OrderMaster20rederDTOConverter;
import com.li.test.dto.CartDTO;
import com.li.test.dto.OrderDTO;
import com.li.test.entities.OrderDetail;
import com.li.test.entities.OrderMaster;
import com.li.test.entities.ProductInfo;
import com.li.test.enums.OrderStatusEnum;
import com.li.test.enums.PayStatusEnum;
import com.li.test.enums.ResultEnum;
import com.li.test.exception.SellException;
import com.li.test.repository.OrderDetailRepository;
import com.li.test.repository.OrderMasterRepository;
import com.li.test.service.ProductInfoService;
import com.li.test.service.WebSocket;
import com.li.test.service.OrderService;
import com.li.test.service.PayService;
import com.li.test.utils.KeyUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductInfoService productservice;
	
	@Autowired
	private OrderDetailRepository orderDetailReposity;
	
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	
	@Autowired
	private PayService PayService;
	
	@Autowired
	private WebSocket websocket;
	
	@Override
	@Transactional
	public OrderDTO oncreate(OrderDTO orderDTO) {
		
		String orderId=KeyUtil.genUniqueKey();
		
		BigDecimal orderAmount=new BigDecimal("0.00");
		
		
		//List<CartDTO> cartDTOList=new ArrayList<>();
		
		//1.查询商品，查询商品的数量，价格
		for(OrderDetail orderDetail:orderDTO.getOrderDetailsList()) {
			Optional<ProductInfo> productInfo=productservice.findOne(orderDetail.getProductId());
		    if(!productInfo.isPresent()) {
		    	throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		    }
		    ProductInfo productInfo2=productInfo.get();
		  //2.计算总价
		    orderAmount=productInfo2.getProductPrice()
		    		.multiply(new BigDecimal(orderDetail
		    				.getProductQuantity())).add(orderAmount);
		    //订单详情入库
		    orderDetail.setDetailId(KeyUtil.genUniqueKey());
		    orderDetail.setOrderId(orderId);
		    BeanUtils.copyProperties(productInfo2, orderDetail);
		    orderDetailReposity.save(orderDetail);
		    
		   // CartDTO cartDTO=new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
		   // cartDTOList.add(cartDTO);
		}
		
		
		//3.写入订单数据库（OrderMaster和OrderDeatil）
		OrderMaster orderMaster=new OrderMaster();
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster);
		orderMaster.setOrderAmount(orderAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMasterRepository.save(orderMaster);
		
		//4.扣库存
		List<CartDTO> cartDTOList=orderDTO.getOrderDetailsList().stream().map(e->
		new CartDTO(e.getProductId(),e.getProductQuantity()))
		.collect(Collectors.toList());
		productservice.decreaseStock(cartDTOList);
		
		//发送websocket消息
		websocket.sendMessage(orderDTO.getOrderId());
		
		return orderDTO;
	}

	@Override
	public OrderDTO findOne(String orderId) {
		// TODO Auto-generated method stub
		Optional<OrderMaster> orderMaster=orderMasterRepository.findById(orderId);
		if(!orderMaster.isPresent()) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		
		List<OrderDetail> orderDetailList=orderDetailReposity.findByOrderId(orderId);
		if (CollectionUtils.isEmpty(orderDetailList)) {
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		OrderDTO orderDTO = new OrderDTO();
		
		BeanUtils.copyProperties(orderMaster.orElse(null),orderDTO );
		orderDTO.setOrderDetailsList(orderDetailList);
		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<OrderMaster> orderMasterPage=orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
		List<OrderDTO> orderDTOs=OrderMaster20rederDTOConverter.convert(orderMasterPage.getContent());
		Page<OrderDTO> orderDTOPage=new PageImpl<OrderDTO>(orderDTOs,pageable,orderMasterPage.getTotalElements());
		return orderDTOPage;
	}
      //取消订单
	@Override
	@Transactional
	public OrderDTO cancel(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		OrderMaster orderMaster=new OrderMaster();
		
		//判断订单状态
		if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		
		//修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster ordermaster=orderMasterRepository.save(orderMaster);
		if (ordermaster==null) {
			log.error("【取消订单】，更新失败，orderMaster={}",orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		
		//返回库存
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailsList())) {
			log.error("【取消订单】订单中无商品详情，orderDTO={}",orderDTO);
			throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
		}
		List<CartDTO> cartDTOList=orderDTO.getOrderDetailsList().stream()
				.map(e->new CartDTO(e.getProductId(), e.getProductQuantity()))
				.collect(Collectors.toList());
		productservice.increaseStock(cartDTOList);
		//如果已支付，需要退款
		if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
        		PayService.refund(orderDTO);
		}
		return orderDTO;
	}
    //完结订单
	@Override 
	@Transactional
	public OrderDTO finished(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		//判断订单状态
		if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
                log.error("【完结订单】订单状态不正确，orderid={}，orderstatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
                throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		
		//修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		OrderMaster orderMaster=new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster orderMaster2=orderMasterRepository.save(orderMaster);
		if (orderMaster2==null) {
			log.error("【完结订单】 更新失败，orderMaster={}",orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO paid(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		//判断订单状态
		if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【订单支付完成】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//判断支付状态
		if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("【订单支付完成】订单支付状态不正确，orderDTO={}",orderDTO);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}
		//修改支付状态
		orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMaster orderMaster=new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster orderMaster2=orderMasterRepository.save(orderMaster);
		if (orderMaster2==null) {
			log.error("【订单支付完成】更新失败，orderMaster={}",orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(Pageable pageable) {
		// TODO Auto-generated method stub
		Page<OrderMaster> orderMasterPage=orderMasterRepository.findAll(pageable);
		
		List<OrderDTO> orderDTOs=OrderMaster20rederDTOConverter.convert(orderMasterPage.getContent());

		return new PageImpl<OrderDTO>(orderDTOs,pageable,orderMasterPage.getTotalElements());
	}

}
