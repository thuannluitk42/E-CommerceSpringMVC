package com.thuannluit.pethouse.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.thuannluit.pethouse.dao.UserDao;
import com.thuannluit.pethouse.entity.Categories;
import com.thuannluit.pethouse.entity.Users;
import com.thuannluit.pethouse.service.CategoryService;
import com.thuannluit.pethouse.service.CustomerService;
import com.thuannluit.pethouse.util.SystemConstant;
import com.thuannluit.pethouse.util.Utility;

@Controller
public class HomeController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	UserDao  userDao;

	private Utility helperClass = new Utility();

	@RequestMapping(path = { "/index", "/" }, method = RequestMethod.GET)
	public String showHomePage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		model.addAttribute("isIndex", true);
//		List<Users> ls = userDao.getUsers("tuitenteo", SystemConstant.ACTIVE_ACCOUNT);
//		for (Users users : ls) {
//			System.out.println(users.toString());
//		}
		return "client/index";
	}

	@RequestMapping(path = { "/login" }, method = RequestMethod.GET)
	public String viewLoginPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		model.addAttribute("isLogin", true);
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

	@RequestMapping(path = "/404", method = { RequestMethod.GET })
	public String show404Page(Model model) {
		System.out.println("HomeController.show404Page");
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/404";
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
		model.addAttribute("isLogin", true);
		return "client/login";
	}
	
	@RequestMapping(path = { "/return-policy"}, method = RequestMethod.GET)
	public String showReturnPolicyPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/return_policy";
	}
	
	@RequestMapping(path = { "/shipping-policy"}, method = RequestMethod.GET)
	public String showShippingPolicyPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/shipping_policy";
	}
	@RequestMapping(path = { "/privacy-policy"}, method = RequestMethod.GET)
	public String showPrivacyPolicyPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/privacy_policy";
	}
	@RequestMapping(path = { "/payment-method"}, method = RequestMethod.GET)
	public String showPaymentMethodPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/payment_method";
	}
	@RequestMapping(path = { "/refund-policy"}, method = RequestMethod.GET)
	public String showRefundPolicyPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/refund_policy";
	}
	
	@RequestMapping(path = { "/loyal-customer"}, method = RequestMethod.GET)
	public String showMembershipPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/loyal_customer";
	}
	
	@RequestMapping(path = { "/terms-of-service"}, method = RequestMethod.GET)
	public String showTermsOfServicePage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/terms_of_service";
	}
}
