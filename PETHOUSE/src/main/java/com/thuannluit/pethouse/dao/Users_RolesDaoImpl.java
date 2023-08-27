package com.thuannluit.pethouse.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thuannluit.pethouse.entity.UsersRoles;

@Repository
@Transactional
public class Users_RolesDaoImpl implements Users_RolesDao {
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(Users_RolesDaoImpl.class);

	public void setRoleCustomer(UsersRoles roleCustomer) {
		logger.info("Users_RolesDaoImpl.setRoleCustomer" + roleCustomer.toString());
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(roleCustomer);
	}

}
