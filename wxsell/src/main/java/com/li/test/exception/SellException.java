package com.li.test.exception;

import com.li.test.enums.ResultEnum;

public class SellException extends RuntimeException{
 
	private Integer code;

	public SellException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());
		
		this.code=resultEnum.getCode();
	}

	public SellException(Integer code2, String defaultMessage) {
		// TODO Auto-generated constructor stub
		super(defaultMessage);
		this.code=code2;
	}
	

}
