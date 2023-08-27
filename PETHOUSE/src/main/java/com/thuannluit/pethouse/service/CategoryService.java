package com.thuannluit.pethouse.service;

import java.util.List;

import com.thuannluit.pethouse.entity.Categories;

public interface CategoryService {
	List<Categories> getCategories();

	void saveCategory(Categories theCategories);

	Categories getCategory(int theId);

	void deleteCategory(int theId);
}
