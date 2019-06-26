package com.li.test.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void set(HttpServletResponse response,
			               String name,
			               String value,
			               int maxAge) {
		Cookie cookie=new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	
	public static Cookie get(HttpServletRequest request,String name) {
		Map<String, Cookie>cookiMap=readCookieMap(request);
		if (cookiMap.containsKey(name)) {
			return cookiMap.get(name);
		}
		else {
			return null;
		}
	}
	
	//封装Cookie成Map
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap=new HashMap<>();
		Cookie[] cookies=request.getCookies();
		if (cookies!=null) {
			for(Cookie cookie:cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
