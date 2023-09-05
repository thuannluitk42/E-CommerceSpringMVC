package com.thuannluit.pethouse.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thuannluit.pethouse.dao.UserDao;
import com.thuannluit.pethouse.entity.Categories;
import com.thuannluit.pethouse.entity.Products;
import com.thuannluit.pethouse.service.CategoryService;
import com.thuannluit.pethouse.service.ProductService;

@Controller
public class ShopController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;

	//@RequestMapping(path = "/product-detail/{productId}", method = { RequestMethod.GET })
	@RequestMapping(path = "/product-detail", method = { RequestMethod.GET })
	//public String showProductDetailPage(Model model, @PathVariable int productId) {
		public String showProductDetailPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
//		List<Products> listProduct = productService.findProductsById(String.valueOf(productId));
//		model.addAttribute("products", listProduct);
		return "client/product_detail";
	}
	
	@RequestMapping(path = "/shop", method = { RequestMethod.GET })
		public String showAllProductPage(Model model) {
		List<Categories> categories = this.categoryService.getCategories();
		model.addAttribute("listCategory", categories);
		return "client/shop";
	}

}
