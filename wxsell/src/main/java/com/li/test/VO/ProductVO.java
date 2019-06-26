package com.li.test.VO;

import java.util.List;

import lombok.Data;

@Data
public class ProductVO {

	private String categoryName;
	
	private Integer categoryType;
	
	private List<ProductInfoVO> productInfoVOs;
}
