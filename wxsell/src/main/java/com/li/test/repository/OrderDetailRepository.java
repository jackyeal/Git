package com.li.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.li.test.entities.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

	List<OrderDetail> findByOrderId(String orderId);
}
