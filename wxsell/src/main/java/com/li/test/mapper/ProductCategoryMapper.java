package com.li.test.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;

public interface ProductCategoryMapper {

	@Insert("insert into product_category(category_name,category_type) values (#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
	int insertByMap(Map<String, Object> map);
}
