package com.thuannluit.pethouse.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class Utility {
	public String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
	
	public boolean checkNullOrEmpty(String str) {
		str = str.trim();
		// chuoi khong null thi true
		if (StringUtils.hasText(str)) {
			return true;
		} else {
			return false;
		}
		
	}
}
