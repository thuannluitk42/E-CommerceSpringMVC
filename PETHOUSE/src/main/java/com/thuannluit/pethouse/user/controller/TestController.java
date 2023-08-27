package com.thuannluit.pethouse.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thuannluit.pethouse.dao.UserDao;
import com.thuannluit.pethouse.dao.UserDaoImpl;
import com.thuannluit.pethouse.entity.Users;
import com.thuannluit.pethouse.util.SystemConstant;

@Component
public class TestController {

	@Autowired
	UserDao  uruDaoImpl;

	public void test() {
		//List<Users> ls = uruDaoImpl.getUsers("tuitenteo", SystemConstant.ACTIVE_ACCOUNT);
	}
}
