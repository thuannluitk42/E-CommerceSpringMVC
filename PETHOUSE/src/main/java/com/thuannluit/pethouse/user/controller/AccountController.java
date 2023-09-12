package com.thuannluit.pethouse.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.thuannluit.pethouse.dto.Login;
import com.thuannluit.pethouse.dto.UserInfo;
import com.thuannluit.pethouse.entity.Categories;
import com.thuannluit.pethouse.entity.Roles;
import com.thuannluit.pethouse.entity.Users;
import com.thuannluit.pethouse.service.CategoryService;
import com.thuannluit.pethouse.service.CustomerService;
import com.thuannluit.pethouse.util.Utility;

import java.util.Optional;

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

	@RequestMapping(path = { "/admin/manage_user" }, method = RequestMethod.GET)
	public String viewManageUserPage(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Page<UserInfo> usersPage = customerService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("usersPage", usersPage);
		int totalPages = usersPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = new ArrayList<Integer>();
			for (int i = 1; i <= totalPages; i++) {
				pageNumbers.add(i);
			}
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("pageNumbers", pageNumbers);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("userList", usersPage.getContent());
		}
		return "admin/manage_user";
	}

	@RequestMapping(path = { "/admin/detail_user" }, method = RequestMethod.GET)
	public String viewDetailUserPage(Model model, @RequestParam("user_id") Integer user_id) {
		List<Users> customer = customerService.getInfoUserById(user_id);
		model.addAttribute("user", customer.get(0));
		String roleName = "";
		Iterator<Roles> value = customer.get(0).getRoles().iterator();
		while (value.hasNext()) {
			roleName = value.next().getRoleName().toString();
		}
		model.addAttribute("user", customer.get(0));
		model.addAttribute("role_user", roleName);
		return "admin/detail_user";
	}

	@RequestMapping(path = {"/admin/updateInfoUser" }, 
					method = RequestMethod.POST, 
					produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateInfoUserUseAjax(@RequestBody Users user, HttpServletRequest request) {
		
		Users u = customerService.updateInfoUser(user);
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(u);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ajaxResponse;
	}
	
	@RequestMapping(path = { "/admin/delete_user" }, method = RequestMethod.POST)
	public String deleteUser(Model model, @RequestParam("user_id") Integer user_id) {
		boolean result =  customerService.processDeleteUserById(user_id);
		model.addAttribute("result_delete_user", result);
		return "admin/manage_user";
	}

	@RequestMapping(path = { "/login-process" }, method = RequestMethod.POST)
	public String processLogin(@ModelAttribute Login login, Model model, HttpSession session,
			HttpServletRequest request) {

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
		model.addAttribute("isIndex", true);
		return "client/index";
	}

	@RequestMapping(path = "/login_failure_handler", method = { RequestMethod.GET })
	public String loginFailureHandler(Model model) {
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

	@RequestMapping(path = { "/create_user" }, method = RequestMethod.POST)
	public String createUser(Users userSystem, Model model, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {
		customerService.registerCustomer(userSystem);
		customerService.sendVerificationEmail(helperClass.getSiteURL(request), userSystem);
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/register_success";
	}

	@RequestMapping(path = { "/create_customer" }, method = RequestMethod.POST)
	public String createCustomer(Users customer, Model model, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {
		customerService.registerCustomer(customer);
		customerService.sendVerificationEmail(helperClass.getSiteURL(request), customer);
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/register_success";
	}

	@RequestMapping(path = { "/verify" }, method = RequestMethod.GET)
	public String verifyUser(@RequestParam("code") String code) {
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
