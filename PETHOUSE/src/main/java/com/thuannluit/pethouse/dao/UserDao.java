package com.thuannluit.pethouse.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.thuannluit.pethouse.dto.Login;
import com.thuannluit.pethouse.dto.UserInfo;
import com.thuannluit.pethouse.entity.Users;


public interface UserDao {

	void saveCustomer(Users customer);

	List<Users> findUserByUsernameAndByEnabled(String username ,boolean activeAccount);
	
	Users findUserByVerificationCode(String code);
	
	List<Users> findUserByUsernameAndByPassswordAndByEnabled(Login login ,boolean activeAccount);

	List<Users> getListAccount();

	Page<UserInfo> pagingOfUser(Pageable pageable);

	List<Users> getAccountById(Integer user_id);

	Users updateInfoCustomer(Users user);

	boolean disableUser(Integer user_id);

	List<Users> findUserByUsername(String username);

}

//public interface UserDao extends CrudRepository<Users, Long> {
//	@Query("SELECT u FROM users u WHERE u.verificationCode = ?1")
//	Users findUserByVerificationCode(String code);
//
//	@Query("SELECT u FROM users u WHERE u.username = ?1 and u.enabled = ?2")
//	List<Users> findUserByUsernameAndByStatus(String username, boolean inactiveAccount);
//}
