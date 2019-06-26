package com.li.test.service;

import java.util.List;
import java.util.Optional;

import com.li.test.entities.ProductCategory;

public interface ProductCategoryService {

	Optional<ProductCategory> findById(Integer categoryId);
	
	List<ProductCategory> findAll();
	
	List<ProductCategory> update(ProductCategory productCategory);
	
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);

	ProductCategory save(ProductCategory productCategory);

}
