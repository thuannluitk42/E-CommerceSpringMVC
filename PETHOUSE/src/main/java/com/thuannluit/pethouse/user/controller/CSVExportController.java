package com.thuannluit.pethouse.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.thuannluit.pethouse.dto.UserInfo;
import com.thuannluit.pethouse.service.CustomerService;
import com.thuannluit.pethouse.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class CSVExportController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private FileService fileService;

	@GetMapping("/admin/exportUserCSV")
	public void exportCSV(HttpServletResponse response) throws IOException {
		// Get data to export
		List<UserInfo> employees = customerService.getAccounts();

		String fileName = fileService.generateFileName("user");

		// Set response headers
		fileService.setResponseHeaders(response, fileName);

		String[] arrTitleColumn = { "USERID", "FullName", "Email", "PhoneNumber", "RoleName", "Status" };
		fileService.setTitleColumnFile(response.getWriter(), arrTitleColumn);

		// Write CSV content to response
		fileService.writeCSVContent(response.getWriter(), employees);
	}

}
