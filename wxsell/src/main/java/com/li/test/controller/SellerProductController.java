package com.li.test.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.li.test.constant.CookieConstant;
import com.li.test.constant.Redisconstant;
import com.li.test.entities.ProductCategory;
import com.li.test.entities.ProductInfo;
import com.li.test.exception.SellException;
import com.li.test.form.ProductForm;
import com.li.test.service.ProductCategoryService;
import com.li.test.service.ProductInfoService;
import com.li.test.utils.CookieUtil;
import com.li.test.utils.KeyUtil;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

	@Autowired
	private ProductInfoService productservice;

	@Autowired
	private ProductCategoryService categoryservice;
	
	
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
		Page<ProductInfo> productInfoPage = productservice.findAll(PageRequest.of(page - 1, size));
		map.put("productInfoPage", productInfoPage);
		map.put("currentPage", page);
		map.put("size", size);
		return new ModelAndView("product/list", map);
	}

	@GetMapping("/on_sale")
	public ModelAndView onSale(@RequestParam("productId") String productId, Map<String, Object> map) {
		try {
			productservice.onSale(productId);

		} catch (SellException e) {
			// TODO: handle exception
			map.put("msg", e.getMessage());
			map.put("url", "/seller/product/list");
			return new ModelAndView("common/error", map);
		}
		map.put("url", "/seller/product/list");
		return new ModelAndView("common/success", map);
	}

	@GetMapping("/off_sale")
	public ModelAndView offSale(@RequestParam("productId") String productId, Map<String, Object> map) {
		try {
			productservice.offSale(productId);

		} catch (SellException e) {
			// TODO: handle exception
			map.put("msg", e.getMessage());
			map.put("url", "/seller/product/list");
			return new ModelAndView("common/error", map);
		}
		map.put("url", "/seller/product/list");
		return new ModelAndView("common/success", map);
	}

	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
			HttpServletResponse response,
			Map<String, Object> map) {
		
		//设置token到redis
		
		//设置token到cookie
		
		if (!StringUtils.isEmpty(productId)) {
			Optional<ProductInfo> productInfo = productservice.findOne(productId);
			ProductInfo productInfo2 = productInfo.get();
			map.put("productInfo", productInfo2);
		}

		// 查询所有的类目
		List<ProductCategory> categoryList = categoryservice.findAll();
		map.put("categoryList", categoryList);

		return new ModelAndView("product/index", map);
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid ProductForm form, BindingResult bindingResult, Map<String, Object> map) {
		if (bindingResult.hasErrors()) {
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/seller/product/index");
			return new ModelAndView("common/error", map);
		}
		ProductInfo productInfo = new ProductInfo();
		try {
			if (!StringUtils.isEmpty(form.getProductId())) {
				Optional<ProductInfo> productInfo2 = productservice.findOne(form.getProductId());
				productInfo = productInfo2.get();
			} else {
				form.setProductId(KeyUtil.genUniqueKey());
			}

			BeanUtils.copyProperties(form, productInfo);

			productservice.save(productInfo);
		} catch (SellException e) {
			// TODO: handle exception
			map.put("msg", e.getMessage());
			map.put("url", "/seller/product/index");
			return new ModelAndView("common/error", map);
		}

		map.put("url", "/seller/product/list");
		return new ModelAndView("common/success", map);
	}
}
