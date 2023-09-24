package com.thuannluit.pethouse.util;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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

	public String cvtDDMMYYYY(String inputDateString) {
		String outputDateString = "";
		// Create a SimpleDateFormat object to parse the input string
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");

		// Create a SimpleDateFormat object to format the output string
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {
			// Parse the input string to a Date object
			Date date = inputDateFormat.parse(inputDateString);

			// Format the Date object to the desired output format
			outputDateString = outputDateFormat.format(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return outputDateString;
	}
	
}
