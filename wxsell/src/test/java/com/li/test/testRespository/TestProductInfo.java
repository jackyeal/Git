package com.li.test.testRespository;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.li.test.entities.ProductInfo;
import com.li.test.repository.ProductInfoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProductInfo {

	@Autowired
	private ProductInfoRepository repository;
	
	@Test
	public void saveTest() {
		ProductInfo productInfo=new ProductInfo();
		productInfo.setProductId("121");
		productInfo.setProductName("adidas");
		productInfo.setProductDescription("Superstar");
		productInfo.setProductIcon("http://dadsadsj.jpg");
		productInfo.setProductStock(12);
		productInfo.setProductPrice(new BigDecimal(200));
		productInfo.setProductStatus(0);
		productInfo.setCategoryType(2);
		
		ProductInfo result=repository.save(productInfo);
		Assert.assertNotNull(result);
	}
//	@Test
//	public void findByProductStatus()throws Exception{
//		List<ProductInfo> productInfos=repository.findByProductStatus(0);
//		Assert.assertNotEquals(0, productInfos.size());
//	}
}
