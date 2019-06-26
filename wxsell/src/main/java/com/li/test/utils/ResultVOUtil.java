package com.li.test.utils;

import com.li.test.VO.ResultVO;

public class ResultVOUtil {

	public static ResultVO success(Object object) {
		ResultVO resultVO=new ResultVO();
		resultVO.setCode(0);
		resultVO.setMasg("成功");
		resultVO.setDate(object);
		return resultVO;
	}
	
	public static ResultVO success() {
		return success(null);
	}
	public static ResultVO error(Integer code,String msg) {
		ResultVO resultVO=new ResultVO();
		resultVO.setCode(code);
		resultVO.setMasg(msg);
		return resultVO;
		
	}
}
