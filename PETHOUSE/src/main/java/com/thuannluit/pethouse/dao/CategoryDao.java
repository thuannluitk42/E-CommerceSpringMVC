package com.thuannluit.pethouse.dao;

import java.util.List;

import com.thuannluit.pethouse.entity.Categories;

//Let's create Dao layer with CategoryDao interface and it's implementation CategoryDaoImpl.java class. 
//In Spring, we annotate Dao implementation classes with @Repository annotation.
public interface CategoryDao {
	List<Categories> getCategories();

	void saveCategory(Categories theCategory);

	Categories getCategory(int theId);

	void deleteCategory(int theId);
}
