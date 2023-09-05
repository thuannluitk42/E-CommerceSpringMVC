package com.thuannluit.pethouse.service;

import java.util.List;

import com.thuannluit.pethouse.entity.Products;

public interface ProductService {
	List<Products> findProductsById(String productId);
}
