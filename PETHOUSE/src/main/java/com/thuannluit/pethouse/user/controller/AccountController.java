package com.thuannluit.pethouse.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.thuannluit.pethouse.dto.Login;
import com.thuannluit.pethouse.entity.Categories;
import com.thuannluit.pethouse.entity.Users;
import com.thuannluit.pethouse.service.CategoryService;
import com.thuannluit.pethouse.service.CustomerService;
import com.thuannluit.pethouse.util.Utility;

@Controller
public class AccountController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CustomerService customerService;
	private Utility helperClass = new Utility();

	@RequestMapping(path = { "/login" }, method = RequestMethod.GET)
	public String viewLoginPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		model.addAttribute("isLoginPage", true);
		model.addAttribute("login", new Login());
		return "client/login";
	}

	@RequestMapping(path = { "/login-process" }, method = RequestMethod.POST)
	public String processLogin(@ModelAttribute Login login, Model model, HttpSession session, HttpServletRequest request) {
		
		System.out.println("LoginController.processLogin()" + login.toString());
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);

		Utility utility = new Utility();
		boolean checkUsername = utility.checkNullOrEmpty(login.getUsername());
		boolean checkPassword = utility.checkNullOrEmpty(login.getPassword());

		if (checkPassword == false || checkUsername == false) {
			model.addAttribute("messageError", "Vui lòng nhập username hoặc mật khẩu");
			model.addAttribute("isLoginPage", true);
			model.addAttribute("loginError", true);
			return "client/login";
		}

		List<Users> customer = customerService.findCustomerByUsernameAndPassword(login);

		if (CollectionUtils.isEmpty(customer)) {
			model.addAttribute("messageError", "Username hoặc mật khẩu không đúng");
			model.addAttribute("isLoginPage", true);
			model.addAttribute("loginError", true);
			return "client/login";
		}

		session.setAttribute("customerModel", customer.get(0));
		
		return "redirect:index";

	}

	@RequestMapping(path = "/login_success_handler", method = { RequestMethod.GET })
	public String loginSuccessHandler(Model model) {
		System.out.println("HomeController.loginSuccessHandler");
		model.addAttribute("isIndex", true);
		return "client/index";
	}

	@RequestMapping(path = "/login_failure_handler", method = { RequestMethod.GET })
	public String loginFailureHandler(Model model) {
		System.out.println("HomeController.loginFailureHandler");
		model.addAttribute("loginError", true);
		model.addAttribute("isLoginPage", true);
		return "client/login";
	}

	@RequestMapping(path = { "/register" }, method = RequestMethod.GET)
	public String viewRegisterPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		model.addAttribute("users", new Users());
		model.addAttribute("isRegister", true);
		return "client/register";
	}

	@RequestMapping(path = { "/create_customer" }, method = RequestMethod.POST)
	public String createCustomer(Users customer, Model model, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {
		System.out.println("HomeController.createCustomer: " + customer.toString());
		customerService.registerCustomer(customer);
		customerService.sendVerificationEmail(helperClass.getSiteURL(request), customer);
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/register_success";
	}

	@RequestMapping(path = { "/verify" }, method = RequestMethod.GET)
	public String verifyUser(@RequestParam("code") String code) {
		System.out.println("HomeController.verifyUser: " + code);
		if (customerService.verifyAccount(code)) {
			return "client/verify_success";
		} else {
			return "client/verify_fail";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("customerModel");
		return "client/index";
	}
}
