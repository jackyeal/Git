package com.li.test.service;

import java.util.List;
import java.util.Optional;

import javax.swing.text.Caret;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.li.test.dto.CartDTO;
import com.li.test.entities.ProductInfo;

public interface ProductInfoService {

	Optional<ProductInfo> findOne(String productId);

	/*
	 * 查询所有在架商品
	 */
	List<ProductInfo> findupAll();

	/* 查询所有，分页显示 */
	Page<ProductInfo> findAll(Pageable request);

	ProductInfo save(ProductInfo productInfo);
	
    /*加库存*/
	void increaseStock(List<CartDTO> cartDTOList);
	
	/*减库存*/
	void decreaseStock(List<CartDTO> cartDTOList);
	
	/*上架*/
	ProductInfo   onSale(String productId);
	
	
	/*下架*/
	ProductInfo offSale(String productId);
}