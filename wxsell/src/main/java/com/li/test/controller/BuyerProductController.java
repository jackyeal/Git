package com.li.test.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.li.test.VO.ProductInfoVO;
import com.li.test.VO.ProductVO;
import com.li.test.VO.ResultVO;
import com.li.test.entities.ProductCategory;
import com.li.test.entities.ProductInfo;
import com.li.test.service.ProductCategoryService;
import com.li.test.service.ProductInfoService;
import com.li.test.utils.ResultVOUtil;

@RestController
@RequestMapping("buyer/product")
public class BuyerProductController {

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private ProductInfoService productInfoService;
	
	@RequestMapping("/list")
	public ResultVO list() {
		//查询所有的上架商品
		List<ProductInfo> productInfos=productInfoService.findupAll();
		
		//查询类目(一次性查询)
//		List<Integer> categoryList=new ArrayList<Integer>();
//		//传统方法
//		for(ProductInfo productInfo:productInfos) {
//			categoryList.add(productInfo.getCategoryType());
//		}
		//精简方法，lambda
		List<Integer> categoryTypeList=productInfos.stream()
				.map(e->e.getCategoryType()).collect(Collectors
						.toList());
		//数据拼装
		List<ProductCategory> productCategories=productCategoryService.findByCategoryTypeIn(categoryTypeList);
	    
		List<ProductVO> productVOs=new ArrayList<>();
		for(ProductCategory productCategory:productCategories ) {
			ProductVO productVO = new ProductVO();
			productVO.setCategoryName(productCategory.getCategoryName());
			productVO.setCategoryType(productCategory.getCategoryType());
			
			List<ProductInfoVO> productInfoVOs=new ArrayList<>();
			for (ProductInfo productInfo:productInfos) {
				if(productCategory.getCategoryType().equals(productInfo.getCategoryType())) {
					ProductInfoVO productInfoVO = new ProductInfoVO();
					BeanUtils.copyProperties(productInfo, productInfoVO);
					productInfoVOs.add(productInfoVO);
				}
			}
			productVO.setProductInfoVOs(productInfoVOs);
			productVOs.add(productVO);
			
		}

		return ResultVOUtil.success(productVOs);
		
	
	}
	
	
	
	
	
}
