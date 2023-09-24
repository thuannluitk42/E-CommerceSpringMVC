package com.thuannluit.pethouse.service;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.thuannluit.pethouse.dto.UserInfo;

public interface FileService {

	void setResponseHeaders(HttpServletResponse response, String fileName);

	void writeCSVContent(PrintWriter writer, List<UserInfo> employees);

	String generateFileName(String fileName);

	void setTitleColumnFile(PrintWriter writer, String[] arrTitleColumn);

}
