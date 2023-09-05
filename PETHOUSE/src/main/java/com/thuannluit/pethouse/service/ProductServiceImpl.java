package com.thuannluit.pethouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thuannluit.pethouse.dao.ProductsDao;
import com.thuannluit.pethouse.entity.Products;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductsDao productsDao;

	public List<Products> findProductsById(String productId) {
		return productsDao.getProductById(productId);
	}

}
