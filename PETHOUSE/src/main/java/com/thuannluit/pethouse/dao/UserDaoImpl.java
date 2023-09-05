package com.thuannluit.pethouse.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thuannluit.pethouse.dto.Login;
import com.thuannluit.pethouse.entity.Users;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	public void saveCustomer(Users customer) {
		logger.info("UserDaoImpl.UserDaoImpl" + customer.toString());
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(customer);
	}

	@SuppressWarnings("unchecked")
	public List<Users> findUserByUsernameAndByStatus(String username, boolean statusAccount) {
		logger.info("UserDaoImpl.getUsers: " + username);
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Users> cq = cb.createQuery(Users.class);
		Root<Users> root = cq.from(Users.class);
		cq.select(root);

		if (StringUtils.hasText(username)) {
			Predicate p = cb.equal(root.get("username").as(String.class), username.trim());
			p = cb.and(p, cb.equal(root.get("enabled"), statusAccount));
			cq = cq.where(p);
		}
		Query query = session.createQuery(cq);

		return query.getResultList();
	}

	public Users findUserByVerificationCode(String code) {
		logger.info("UserDaoImpl.findByVerificationCode: " + code);
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Users> cq = cb.createQuery(Users.class);
		Root<Users> root = cq.from(Users.class);
		cq.select(root);

		if (!code.isEmpty()) {
			Predicate p = cb.equal(root.get("verificationCode").as(String.class), code.trim());
			cq = cq.where(p);
		}
		try {
			Query query = session.createQuery(cq);
			return (Users) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Users> findUserByUsernameAndByPassswordAndByStatus(Login login, boolean activeAccount) {
		logger.info("UserDaoImpl.findUserByUsernameAndByPassswordAndByStatus: " );
		
		System.out.println("UserDaoImpl.findUserByUsernameAndByPassswordAndByStatus()"+ login.toString());
		
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Users> cq = cb.createQuery(Users.class);
		Root<Users> root = cq.from(Users.class);
		cq.select(root);

		if (StringUtils.hasText(login.getUsername()) && StringUtils.hasText(login.getPassword())) {

			Predicate p = cb.equal(root.get("username").as(String.class), login.getUsername().trim());
			p = cb.and(p, cb.equal(root.get("password").as(String.class), login.getPassword().trim()));
			p = cb.and(p, cb.equal(root.get("enabled"), activeAccount));
			cq = cq.where(p);
		}
		Query query = session.createQuery(cq);
		return query.getResultList();
	}

}
