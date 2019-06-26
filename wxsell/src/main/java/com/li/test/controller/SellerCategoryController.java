package com.li.test.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.li.test.entities.ProductCategory;
import com.li.test.exception.SellException;
import com.li.test.form.Categoryform;
import com.li.test.service.ProductCategoryService;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

	@Autowired 
	private ProductCategoryService categoryService;
	
	@GetMapping("/list")
	public ModelAndView list(Map<String, Object> map) {
		List<ProductCategory> categoryList=categoryService.findAll();
		map.put("categoryList", categoryList);
		return new ModelAndView("category/list",map);
	}
	
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value="categoryId",required = false) Integer categoryId,
			                  Map<String, Object> map) {
		if(categoryId!=null) {
			Optional<ProductCategory> productCategory=categoryService.findById(categoryId);
			ProductCategory productCategory2=productCategory.get();
			map.put("productCategory", productCategory2);
		}
		return new ModelAndView("category/index",map);
	}
	
	@PostMapping("/save")
	public ModelAndView save(@Valid Categoryform form,
			                 BindingResult bindingResult,
			                 Map<String, Object> map) {
		if(bindingResult.hasErrors()) {
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "seller/category/index");
			return new ModelAndView("common/error",map);
		}
		ProductCategory productCategory=new ProductCategory();
		try {
			if (form.getCategoryId()!=null) {
				Optional<ProductCategory> productCategory2=categoryService.findById(form.getCategoryId());
				productCategory=productCategory2.get();
			}
			BeanUtils.copyProperties(form, productCategory);
			categoryService.save(productCategory);
		} catch (SellException e) {
			// TODO: handle exception
			map.put("msg", e.getMessage());
			map.put("url", "seller/category/index");
			return new ModelAndView("common/error",map);
		}
		map.put("url", "list");
		return new ModelAndView("common/success",map);
	}
}
