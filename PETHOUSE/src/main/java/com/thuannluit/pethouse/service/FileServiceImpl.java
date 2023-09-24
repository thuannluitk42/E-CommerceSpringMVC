package com.thuannluit.pethouse.service;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thuannluit.pethouse.dto.UserInfo;

@Service
@Transactional
public class FileServiceImpl implements FileService {

	public void writeCSVContent(PrintWriter writer, List<UserInfo> employees) {
		// Write data to CSV
		for (UserInfo employee : employees) {
			writer.println(employee.getUserId() + "," + 
						   employee.getFullName() + "," + 
					       employee.getEmail() + "," + 
						   employee.getPhoneNumber() + "," + 
					       employee.getRoleName() + "," + 
						   employee.isStatus());
		}
		writer.close();
	}

	// Set response headers
	public void setResponseHeaders(HttpServletResponse response, String fileName) {
		response.setContentType("text/csv; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	}

	public String generateFileName(String fileName) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formattedDateTime = currentDateTime.format(formatter);
		return "file_" + fileName + formattedDateTime + ".csv";
	}

	public void setTitleColumnFile(PrintWriter writer, String[] arrTitleColumn) {
		String titleColumn = "";
		for (String string : arrTitleColumn) {
			titleColumn += string + ",";
		}
		writer.println(titleColumn);
	};
}
