package com.li.test.entities;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@DynamicUpdate
@Data
@EntityListeners(AuditingEntityListener.class)
public class ProductCategory {

	@Id
	@GeneratedValue
	private Integer categoryId;/* 类目id */

	/* 类目名称 */
	private String categoryName;

	/* 类目编号 */
	private Integer categoryType;
	
	@Column(name="create_time",nullable = false)
	private Date createTime;

	@LastModifiedDate
	private Timestamp updateTime;

}
