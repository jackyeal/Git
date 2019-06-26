package com.li.test.VO;

import lombok.Data;

@Data
public class ResultVO<T> {

	/*错误码*/
	private Integer code;
	
	/*提示信息*/
	private String masg;
	
	/*具体内容*/
	private T date;
}
