package com.thuannluit.pethouse.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.thuannluit.pethouse.dto.Login;
import com.thuannluit.pethouse.entity.Users;

public interface CustomerService {
	void registerCustomer(Users customer);

	void sendVerificationEmail(String siteURL, Users customer) throws UnsupportedEncodingException, MessagingException;

	boolean verifyAccount(String verificationCode);

	List<Users> findCustomerByUsernameAndPassword (Login login);
}
