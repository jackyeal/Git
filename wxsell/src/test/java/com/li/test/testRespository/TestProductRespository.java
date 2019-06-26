package com.li.test.testRespository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.Id;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import com.li.test.entities.ProductCategory;
import com.li.test.repository.ProductCattegoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProductRespository {

	@Autowired
	private ProductCattegoryRepository resRepository;
	
//	@Test
//	public void findOne() {
//		ProductCategory productCategory=resRepository.findOne(1);
//		System.out.println(productCategory.toString());
//	}
//	
//	@Test
//	public void saveTest() {
//		ProductCategory productCategory=new ProductCategory();
//		productCategory.setCategoryName("666");
//		productCategory.setCategoryType(3);
//		resRepository.save(productCategory);
//	}
	@Test
	@Transactional
	public void updateTest() {
		Optional<ProductCategory> productCategory=resRepository.findById(2);
		if(productCategory.isPresent()) {
			ProductCategory	productCategory2=productCategory.get();
			productCategory2.setCategoryName("646");
			productCategory2.setCategoryType(3);
		resRepository.save(productCategory2);
	}}
	
	@Test
	public void findByCategoryTypeInTest() {
		List<Integer> list=Arrays.asList(1,2);
		
		List<ProductCategory> result=resRepository.findByCategoryTypeIn(list);
		Assert.assertNotEquals(0,result.size());
	}
}
