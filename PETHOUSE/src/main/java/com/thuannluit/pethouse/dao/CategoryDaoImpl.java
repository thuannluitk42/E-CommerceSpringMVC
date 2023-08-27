package com.thuannluit.pethouse.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thuannluit.pethouse.entity.Categories;

@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Categories> getCategories() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Categories> cq = cb.createQuery(Categories.class);
		Root<Categories> root = cq.from(Categories.class);
		cq.select(root);
//		
//		if (!username.isempty()) {
//			Predicate p = cb.equal(root.get("username").as(String.class), username.trim());
//				cq = cq.where(p);	
//		}
		Query query = session.createQuery(cq);
		return query.getResultList();
	}

	public void saveCategory(Categories theCategory) {
//        Session currentSession = sessionFactory.getCurrentSession();
//        currentSession.saveOrUpdate(theCustomer);

	}

	public Categories getCategory(int theId) {
//        Session currentSession = sessionFactory.getCurrentSession();
//        Customer theCustomer = currentSession.get(Customer.class, theId);
		return null;
	}

	public void deleteCategory(int theId) {
//        Session session = sessionFactory.getCurrentSession();
//        Customer book = session.byId(Customer.class).load(id);
//        session.delete(book);

	}
}
