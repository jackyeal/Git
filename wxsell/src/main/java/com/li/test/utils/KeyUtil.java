package com.li.test.utils;

import java.util.Random;

/**
 * 生成唯一的主键
 * 格式：时间加随机数
 * @return
 */
public class KeyUtil {

	
	public static String genUniqueKey() {
		Random random=new Random();
		
		Integer number=random.nextInt(900000)+100000;
		
		return System.currentTimeMillis()+String.valueOf(number);
	}
}
