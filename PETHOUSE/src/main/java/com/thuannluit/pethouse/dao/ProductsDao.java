package com.thuannluit.pethouse.dao;

import java.util.List;

import com.thuannluit.pethouse.entity.Products;

public interface ProductsDao {

	List<Products> getProductById(String productId);

}
