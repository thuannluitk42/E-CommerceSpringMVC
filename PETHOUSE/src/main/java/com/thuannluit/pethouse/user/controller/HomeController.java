package com.thuannluit.pethouse.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.thuannluit.pethouse.dao.UserDao;
import com.thuannluit.pethouse.entity.Categories;
import com.thuannluit.pethouse.service.CategoryService;

@Controller
public class HomeController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	UserDao userDao;

	@RequestMapping(path = { "/index", "/" }, method = RequestMethod.GET)
	public String showHomePage(Model model, HttpServletRequest request, HttpSession session) {
		if (request.getSession().getAttribute("customerModel") == null) {
			session.setAttribute("customerModel", null);
		}

		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		model.addAttribute("isIndex", true);
		return "client/index";
	}

	@RequestMapping(path = "/404", method = { RequestMethod.GET })
	public String show404Page(Model model) {
		System.out.println("HomeController.show404Page");
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/404";
	}

	@RequestMapping(path = { "/return-policy" }, method = RequestMethod.GET)
	public String showReturnPolicyPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/return_policy";
	}

	@RequestMapping(path = { "/shipping-policy" }, method = RequestMethod.GET)
	public String showShippingPolicyPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/shipping_policy";
	}

	@RequestMapping(path = { "/privacy-policy" }, method = RequestMethod.GET)
	public String showPrivacyPolicyPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/privacy_policy";
	}

	@RequestMapping(path = { "/payment-method" }, method = RequestMethod.GET)
	public String showPaymentMethodPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/payment_method";
	}

	@RequestMapping(path = { "/refund-policy" }, method = RequestMethod.GET)
	public String showRefundPolicyPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/refund_policy";
	}

	@RequestMapping(path = { "/loyal-customer" }, method = RequestMethod.GET)
	public String showMembershipPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/loyal_customer";
	}

	@RequestMapping(path = { "/terms-of-service" }, method = RequestMethod.GET)
	public String showTermsOfServicePage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/terms_of_service";
	}
}
