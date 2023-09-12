package com.thuannluit.pethouse.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thuannluit.pethouse.dto.Login;
import com.thuannluit.pethouse.dto.UserInfo;
import com.thuannluit.pethouse.entity.Roles;
import com.thuannluit.pethouse.entity.Users;
import com.thuannluit.pethouse.util.Utility;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	Utility utility = new Utility();

	public void saveCustomer(Users customer) {
		logger.info("UserDaoImpl.UserDaoImpl");
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(customer);
	}

	@SuppressWarnings("unchecked")
	public List<Users> findUserByUsernameAndByStatus(String username, boolean statusAccount) {
		logger.info("UserDaoImpl.getUsers");
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
		logger.info("UserDaoImpl.findByVerificationCode");
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
	public List<Users> findUserByUsernameAndByPassswordAndByEnabled(Login login, boolean activeAccount) {
		logger.info("UserDaoImpl.findUserByUsernameAndByPassswordAndByStatus");

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

	@SuppressWarnings("unchecked")
	public List<Users> getListAccount() {
		logger.info("UserDaoImpl.getListAccount");

		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Users> cq = cb.createQuery(Users.class);
		Root<Users> root = cq.from(Users.class);
		cq.select(root);
		Query query = session.createQuery(cq);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Page<UserInfo> pagingOfUser(Pageable pageable) {
		logger.info("UserDaoImpl.pagingOfUser");
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Users> listUser;
		List<UserInfo> listUserInfo = null;

		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Users> cq = cb.createQuery(Users.class);
		Root<Users> root = cq.from(Users.class);
		cq.select(root);
		Query query = session.createQuery(cq);

		if (query.getResultList().size() < startItem) {
			listUser = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, query.getResultList().size());
			listUser = query.getResultList().subList(startItem, toIndex);
			listUserInfo = cvtUserInfoDto(listUser);
		}
		PageImpl<UserInfo> pageOfUserInfo = new PageImpl<UserInfo>(listUserInfo, PageRequest.of(currentPage, pageSize),
				query.getResultList().size());
		return pageOfUserInfo;
	}

	private List<UserInfo> cvtUserInfoDto(List<Users> listUser) {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		UserInfo item;
		for (Users u : listUser) {
			item = new UserInfo();
			item.setUserId(u.getUserId());
			item.setUsername(u.getUsername());
			item.setUrlAvatar("");
			item.setEmail(u.getEmail());

			String roleName = "";
			Iterator<Roles> value = u.getRoles().iterator();
			while (value.hasNext()) {
				roleName = value.next().getRoleName().toString();
			}
			item.setRoleName(roleName);

			item.setStatus(u.isEnabled());
			item.setCreatedAt(utility.cvtDateTime(u.getCreatedAt()));
			userInfos.add(item);
		}
		return userInfos;
	}

	@SuppressWarnings("unchecked")
	public List<Users> getAccountById(Integer user_id) {
		logger.info("UserDaoImpl.getAccountById");

		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Users> cq = cb.createQuery(Users.class);
		Root<Users> root = cq.from(Users.class);
		cq.select(root);

		if (StringUtils.hasText(user_id.toString())) {

			Predicate p = cb.equal(root.get("userId"), user_id);
			cq = cq.where(p);
		}
		Query query = session.createQuery(cq);
		return query.getResultList();
	}

	public Users updateInfoCustomer(Users userUpdate) {
		logger.info("UserDaoImpl.updateInfoCustomer");
		
		Users u = getAccountById(userUpdate.getUserId()).get(0);
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		u.setFullName(userUpdate.getFullName());
		u.setBirthday(userUpdate.getBirthday());
		u.setGender(userUpdate.getGender());
		u.setEmail(userUpdate.getEmail());
		u.setPhoneNumber(userUpdate.getPhoneNumber());
		u.setAddress(userUpdate.getAddress());
		
		currentSession.saveOrUpdate(u);
		
		return u;
	}
	
//	public boolean update(YourClass yourObject) {
//	    Transaction transaction = null;
//	    boolean result = false;
//	    try {
//	        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//	        Session session = sessionFactory.getCurrentSession();
//	        transaction = session.beginTransaction();
//	        session.update(yourObject);
//	        transaction.commit();
//
//	        result = true;
//
//	    } catch (Exception ex) {
//	        ex.printStackTrace();
//	        if (transaction != null) {
//	            transaction.rollback();
//	        }
//	    }
//	    return result;
//	}

}
