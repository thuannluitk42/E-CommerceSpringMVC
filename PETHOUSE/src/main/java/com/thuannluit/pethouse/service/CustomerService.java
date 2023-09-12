package com.thuannluit.pethouse.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.thuannluit.pethouse.dto.Login;
import com.thuannluit.pethouse.dto.UserInfo;
import com.thuannluit.pethouse.entity.Users;

public interface CustomerService {
	void registerCustomer(Users customer);

	void sendVerificationEmail(String siteURL, Users customer) throws UnsupportedEncodingException, MessagingException;

	boolean verifyAccount(String verificationCode);

	List<Users> findCustomerByUsernameAndPassword (Login login);

	List<UserInfo> getAccounts();

	Page<UserInfo> findPaginated(Pageable pageable);

	List<Users> getInfoUserById(Integer user_id);

	Users updateInfoUser(Users user);
}
