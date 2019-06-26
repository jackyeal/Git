package com.li.test.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.li.test.entities.ProductCategory;
import com.li.test.repository.ProductCattegoryRepository;
import com.li.test.service.ProductCategoryService;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCattegoryRepository productCattegoryRepository;
	
	@Override
	public Optional<ProductCategory> findById(Integer categoryId) {
		Optional<ProductCategory> productCategory=productCattegoryRepository.findById(categoryId);
		return productCategory;
	}

	@Override
	public List<ProductCategory> findAll() {
		 List<ProductCategory> productCategoriesList=productCattegoryRepository.findAll();
		return productCategoriesList;
	}

	@Override
	public List<ProductCategory> update(ProductCategory productCategory) {
		productCattegoryRepository.save(productCategory);
		List<ProductCategory> productCategoriesList=productCattegoryRepository.findAll();
		return productCategoriesList;
	}

	@Override
	public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType) {
		List<ProductCategory> ProductCategoryList=productCattegoryRepository.findByCategoryTypeIn(categoryType);
		return ProductCategoryList;
	}


	@Override
	public ProductCategory save(ProductCategory productCategory) {
		productCattegoryRepository.save(productCategory);
		return productCategory;
	}

}
