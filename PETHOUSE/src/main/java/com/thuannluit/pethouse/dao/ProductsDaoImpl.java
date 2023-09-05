package com.thuannluit.pethouse.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.thuannluit.pethouse.entity.Products;

@Repository
@Transactional
public class ProductsDaoImpl implements ProductsDao {
	@Autowired
	private SessionFactory sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(ProductsDaoImpl.class);

	@SuppressWarnings("unchecked")
	public List<Products> getProductById(String productId) {
		logger.info("ProductsDaoImpl.getProductById: " + productId);
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Products> cq = cb.createQuery(Products.class);
		Root<Products> root = cq.from(Products.class);
		cq.select(root);

		if (StringUtils.hasText(productId)) {
			Predicate p = cb.equal(root.get("productId").as(String.class), productId);
			cq = cq.where(p);
		}
		Query query = session.createQuery(cq);

		return query.getResultList();
	}

}
