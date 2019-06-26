package com.li.test.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@DynamicUpdate
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SellerInfo {

	@Id
	@Column(nullable = false)
	private String sellerId;
	
	@Column(nullable = false)
    private String userName;
	
	@Column(nullable = false)
	private String password;
	
	/* 微信openid */
	@Column(nullable = false)
	private String openid;
	
	/* 创建时间 */
	@CreatedDate
	@Column(name="create_time",nullable = false)
	private Date createTime;
	
	/* 更新时间*/
	@LastModifiedDate
	@Column(name="update_time",nullable = false)
	private Date updateTime;
	
}
