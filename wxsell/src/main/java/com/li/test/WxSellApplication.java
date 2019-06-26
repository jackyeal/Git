package com.li.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@MapperScan(basePackages="com.li.test.mapper")
public class WxSellApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxSellApplication.class, args);
	}

}
