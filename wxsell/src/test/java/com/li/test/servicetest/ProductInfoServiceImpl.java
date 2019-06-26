package com.li.test.servicetest;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import com.li.test.entities.ProductInfo;
import com.li.test.enums.ProductStatusEnum;
import com.li.test.repository.ProductInfoRepository;
import com.li.test.service.ProductInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImpl {

	@Autowired
	private ProductInfoService productInfoServiceImpl;
	@Autowired
	private ProductInfoRepository repo;
	@Autowired
	private TaskExecutor executor;


//	@Test
//	public void save() throws Exception {
//		for (int i = 0; i < 20; i++) {
//			executor.execute(new Runnable() {
//				@Override
//				public void run() {
//					for (int i = 0; i < 1; i++) {
//						ProductInfo productInfo = new ProductInfo();
//						productInfo.setProductName("anta");
//						productInfo.setProductDescription("12321");
//						productInfo.setProductIcon("http://dadsadsj.jpg");
//						productInfo.setProductPrice(new BigDecimal(100));
//						productInfo.setProductStock(11);
//						productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
//						productInfo.setCategoryType(1);
//						ProductInfo result = productInfoServiceImpl.save(productInfo);
//					}
//				}
//			});
//		}
//		while (true) {
////			System.out.println("asd");
//		}
////		Assert.assertNotNull(result);
//
//	}
	
//	@Test
//	public void onsale() {
//		ProductInfo resultInfo=productInfoServiceImpl.onSale("123");
//		Assert.assertEquals(ProductStatusEnum.UP, resultInfo.getProductStatusEnum());
//	}
	@Test
	public void offsale() {
		ProductInfo resultInfo=productInfoServiceImpl.offSale("123");
		Assert.assertEquals(ProductStatusEnum.DOWN, resultInfo.getProductStatusEnum());
	}
//	
}
