package com.li.test.form;

import javax.persistence.Id;

import lombok.Data;

@Data
public class Userform {

	@Id
	private String accountName;
	
	private String password;
}
