package com.thuannluit.pethouse.user.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thuannluit.pethouse.dao.UserDao;
import com.thuannluit.pethouse.dao.UserDaoImpl;
import com.thuannluit.pethouse.dto.Login;
import com.thuannluit.pethouse.entity.Users;
import com.thuannluit.pethouse.service.CustomerService;
import com.thuannluit.pethouse.util.SystemConstant;

@Controller
public class TestController {

	@Autowired
	CustomerService customerService;
	@RequestMapping(path = { "/test" }, method = RequestMethod.GET)
	public void test() {
		Login lg = new Login();
		lg.setUsername("tuitenteo");
		lg.setPassword("tuitenteo");
		List<Users> ls = customerService.findCustomerByUsernameAndPassword(lg);
		for (Users users : ls) {
			System.out.println("TestController.test()" + users.toString());
		}
	}
}
