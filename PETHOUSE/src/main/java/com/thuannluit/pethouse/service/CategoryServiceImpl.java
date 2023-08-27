package com.thuannluit.pethouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thuannluit.pethouse.dao.CategoryDao;
import com.thuannluit.pethouse.entity.Categories;

@Service
@Transactional
// We are using @Transactional annotation which is applied at service layer for transaction support. 
// @Service annotation used to annotate service layer implementation classes as in below file.
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;

	public List<Categories> getCategories() {
		return categoryDao.getCategories();
	}

	public void saveCategory(Categories theCategories) {
		categoryDao.saveCategory(theCategories);
	}

	public Categories getCategory(int theId) {
		return categoryDao.getCategory(theId);
	}

	public void deleteCategory(int theId) {
		categoryDao.deleteCategory(theId);
	}

}
