package com.li.test.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class UserLogin {

	@Id
	private String accountName;
	
	private String password;
}
