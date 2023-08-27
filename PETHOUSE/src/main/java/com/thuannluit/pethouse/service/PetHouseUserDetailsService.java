package com.thuannluit.pethouse.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.thuannluit.pethouse.dao.UserDao;
import com.thuannluit.pethouse.dto.PetHouseUserDetails;
import com.thuannluit.pethouse.entity.Users;
import com.thuannluit.pethouse.util.SystemConstant;

@Service
@Transactional
public class PetHouseUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(PetHouseUserDetailsService.class);
    @Autowired
	UserDao userDao;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("PetHouseUserDetailsService.loadUserByUsername: " + username);
		List<Users> listUser = this.userDao.findUserByUsernameAndByStatus(username, SystemConstant.ACTIVE_ACCOUNT);
		if (CollectionUtils.isEmpty(listUser)) {
			throw new UsernameNotFoundException("Người dùng không tồn tại");
		}
		Users users = listUser.get(0);
		// push thong tin user vao security cua spring
		return new PetHouseUserDetails(users);
	}

	public boolean addUser(Users users) {
		// TODO Auto-generated method stub
		return false;
	}
}
