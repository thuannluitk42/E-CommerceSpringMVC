package com.thuannluit.pethouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thuannluit.pethouse.dao.CategoryDao;
import com.thuannluit.pethouse.dao.RolesDao;
import com.thuannluit.pethouse.entity.Roles;

@Service
@Transactional
public class RolesServiceImpl implements RolesService {
	@Autowired
	private RolesDao rolesDao;

	public List<Roles> getRoles() {
		return rolesDao.getRoles();
	}

}
