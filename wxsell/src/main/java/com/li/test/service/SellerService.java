package com.li.test.service;

import com.li.test.entities.SellerInfo;

public interface SellerService {

	SellerInfo findSellerInfoByOpenid(String openid);
}
