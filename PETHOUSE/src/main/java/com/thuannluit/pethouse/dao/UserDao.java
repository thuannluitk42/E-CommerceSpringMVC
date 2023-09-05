package com.thuannluit.pethouse.dao;

import java.util.List;

import com.thuannluit.pethouse.dto.Login;
import com.thuannluit.pethouse.entity.Users;


public interface UserDao {

	void saveCustomer(Users customer);

	List<Users> findUserByUsernameAndByStatus(String username ,boolean activeAccount);
	
	Users findUserByVerificationCode(String code);
	
	List<Users> findUserByUsernameAndByPassswordAndByStatus(Login login ,boolean activeAccount);

}

//public interface UserDao extends CrudRepository<Users, Long> {
//	@Query("SELECT u FROM users u WHERE u.verificationCode = ?1")
//	Users findUserByVerificationCode(String code);
//
//	@Query("SELECT u FROM users u WHERE u.username = ?1 and u.enabled = ?2")
//	List<Users> findUserByUsernameAndByStatus(String username, boolean inactiveAccount);
//}
