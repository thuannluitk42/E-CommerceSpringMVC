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

import com.thuannluit.pethouse.entity.Roles;

@Repository
@Transactional
public class RolesDaoImpl implements RolesDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Roles> getRoles() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Roles> cq = cb.createQuery(Roles.class);
		Root<Roles> root = cq.from(Roles.class);
		cq.select(root);
		Query query = session.createQuery(cq);
		return query.getResultList();
	}

}
