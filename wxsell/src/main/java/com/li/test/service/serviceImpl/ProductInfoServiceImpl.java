package com.li.test.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.li.test.dto.CartDTO;
import com.li.test.entities.ProductInfo;
import com.li.test.enums.ProductStatusEnum;
import com.li.test.enums.ResultEnum;
import com.li.test.exception.SellException;
import com.li.test.repository.ProductInfoRepository;
import com.li.test.service.ProductInfoService;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductInfoRepository productInfoRepository;

	@Override
	public Optional<ProductInfo> findOne(String productId) {
		return productInfoRepository.findById(productId);
	}

	@Override
	public List<ProductInfo> findupAll() {
		return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		return productInfoRepository.findAll(pageable);
	}

	@Override
	public ProductInfo save(ProductInfo productInfo) {
		return productInfoRepository.save(productInfo);
	}

	@Override
	@Transactional
	public void increaseStock(List<CartDTO> cartDTOList) {
		// TODO Auto-generated method stub
		for (CartDTO cartDTO:cartDTOList) {
			Optional<ProductInfo> productInfo=productInfoRepository.findById(cartDTO.getProductId());
		    if (productInfo==null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
		    ProductInfo productInfo2=productInfo.get();
		    Integer result=productInfo2.getProductStock()+cartDTO.getProductQuantity();
		    productInfo2.setProductStock(result);
		    productInfoRepository.save(productInfo2);
		}
	}

	@Override
	@Transactional
	public void decreaseStock(List<CartDTO> cartDTOList) {
		// TODO Auto-generated method stub
		for(CartDTO cartDTO:cartDTOList) {
			Optional<ProductInfo> productInfo=productInfoRepository.findById(cartDTO.getProductId());
			if(!productInfo.isPresent()) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			ProductInfo productInfo1=productInfo.get();
			Integer resultInteger=productInfo1.getProductStock() - cartDTO.getProductQuantity();
			if(resultInteger<0) {
				throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
			}
			productInfo1.setProductStock(resultInteger);
			productInfoRepository.save(productInfo1);
		}
	}

	@Override
	public ProductInfo onSale(String productId) {
		// TODO Auto-generated method stub
		Optional<ProductInfo> productInfo=productInfoRepository.findById(productId);
		if (!productInfo.isPresent()) {
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}
		ProductInfo productInfo2=productInfo.get();
		if (productInfo2.getProductStatusEnum()==ProductStatusEnum.UP) {
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		
		//更新
		productInfo2.setProductStatus(ProductStatusEnum.UP.getCode());
		
		return productInfoRepository.save(productInfo2);
	}

	@Override
	public ProductInfo offSale(String productId) {
		// TODO Auto-generated method stub
		Optional<ProductInfo> productInfo=productInfoRepository.findById(productId);
		if (!productInfo.isPresent()) {
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}
		ProductInfo productInfo2=productInfo.get();
		if (productInfo2.getProductStatusEnum()==ProductStatusEnum.DOWN) {
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		
		//更新
		productInfo2.setProductStatus(ProductStatusEnum.DOWN.getCode());
		
		return productInfoRepository.save(productInfo2);
	}
	

}
