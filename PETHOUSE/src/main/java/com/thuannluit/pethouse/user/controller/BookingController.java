package com.thuannluit.pethouse.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thuannluit.pethouse.entity.Categories;
import com.thuannluit.pethouse.service.CategoryService;

@Controller
public class BookingController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(path = { "/booking" }, method = RequestMethod.GET)
	public String viewBookingPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		model.addAttribute("isBook", true);
		return "client/booking";
	}
}
