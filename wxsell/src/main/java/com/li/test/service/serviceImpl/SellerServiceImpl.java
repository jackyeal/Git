package com.li.test.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.li.test.entities.SellerInfo;
import com.li.test.repository.SellerInfoRepository;
import com.li.test.service.SellerService;

public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerInfoRepository sellerRepository;
	@Override
	public SellerInfo findSellerInfoByOpenid(String openid) {
		// TODO Auto-generated method stub
		return sellerRepository.findByOpenid(openid);
	}

}
