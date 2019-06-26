package com.li.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.li.test.entities.SellerInfo;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

	SellerInfo  findByOpenid(String openid);
}
