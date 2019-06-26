package com.li.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.li.test.entities.ProductCategory;

public interface ProductCattegoryRepository extends JpaRepository<ProductCategory, Integer> {


	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);


}
