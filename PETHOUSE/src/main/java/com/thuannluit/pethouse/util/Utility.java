package com.thuannluit.pethouse.util;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	public String cvtDateTime(Date inputDate) {
		String outputDate = null;
		// Define the desired output format
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

		// Format the Date object to the desired format
		outputDate = outputFormat.format(inputDate);

		return outputDate;
	}
}
