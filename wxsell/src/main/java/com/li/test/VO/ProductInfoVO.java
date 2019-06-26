package com.li.test.VO;

import java.math.BigDecimal;

import lombok.Data;
/*
 * 5-3 15:00
 */
@Data
public class ProductInfoVO {

	private String productId;
	
	private String productName;
	
	private BigDecimal productPrice;
	
	private String productDescription;
	
	private String productIcon;
}
