package com.li.test.servicetest;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import com.li.test.entities.ProductCategory;
import com.li.test.mapper.ProductCategoryMapper;
import com.li.test.repository.ProductCattegoryRepository;
import com.li.test.service.ProductCategoryService;

import javassist.expr.NewArray;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestCategory {

	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ProductCattegoryRepository repo;
	
	@Autowired
	private ProductCategoryMapper mapper;

//	@Test
//	public void findById () {
//		Optional<ProductCategory> productCategory=productCategoryService.findById(1);
//	    Assert.assertEquals(new Integer(1),productCategory);
//		
//	}
//	
//	@Test
//	public void findall() {
//		List<ProductCategory> productCategories=productCategoryService.findAll();
//		System.out.println(productCategories);
//		//Assert.assertNotEquals(0, productCategories.size());
//	}

//	@Test
//	public void update() {
//	
//		
//	}
//	
//	@Test
//	public void findBycate() {
//		List<ProductCategory> productCategories=productCategoryService.findByCategoryTypeIn(Arrays.asList(3));
//		System.out.println(productCategories);
//		//Assert.assertNotEquals(0, productCategories.size());
//	}

//	@Test
//	public void save() {
//		ProductCategory orElse = repo.findOne(Example.of(productCategory)).orElse(null);
//		orElse.setCategoryName("www	");
////		ProductCategory productCategory=new ProductCategory();
////		productCategory.setCategoryId(13);
////		productCategory.setCategoryName("ssss");
//		ProductCategory productCategory2 = productCategoryService.save(orElse);
//		Assert.assertNotNull(productCategory2);
//	}

	@Test
	public void insertByMap()throws Exception{
		Map<String, Object> map=new HashMap<>();
		map.put("category_name", "春季爆款");
		map.put("category_type",10);
		int result=mapper.insertByMap(map);
		Assert.assertEquals(1, result);
	}
}
