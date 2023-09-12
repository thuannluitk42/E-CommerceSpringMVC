package com.thuannluit.pethouse.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.thuannluit.pethouse.dao.UserDao;
import com.thuannluit.pethouse.dao.Users_RolesDao;
import com.thuannluit.pethouse.dto.Login;
import com.thuannluit.pethouse.dto.UserInfo;
import com.thuannluit.pethouse.entity.Roles;
import com.thuannluit.pethouse.entity.Users;
import com.thuannluit.pethouse.entity.UsersRoles;
import com.thuannluit.pethouse.util.SystemConstant;
import com.thuannluit.pethouse.util.Utility;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private Users_RolesDao users_RolesDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JavaMailSender mailSender;

	Utility utility = new Utility();

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	public void registerCustomer(Users customer) {
		initDataDefaultCustomer(customer);
		userDao.saveCustomer(customer);
		UsersRoles roleCustomer = initRoleDefaultCustomer(customer);
		users_RolesDao.setRoleCustomer(roleCustomer);
		logger.info("CustomerServiceImpl.registerCustomer: " + customer.toString());
	}

	public String encodePassword(String password) {
		return passwordEncoder.encode(password);

	}

	public void sendVerificationEmail(String siteURL, Users customer)
			throws UnsupportedEncodingException, MessagingException {
		logger.info("CustomerServiceImpl.sendVerificationEmail: " + siteURL);

		String toAddress = customer.getEmail();
		String fromAddress = "levanthuan23031998@gmail.com";
		String senderName = "PETHOUSE";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "PETHOUSE.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", customer.getUsername());
		String verifyURL = siteURL + "/verify?code=" + customer.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);

	}

	public boolean verifyAccount(String verificationCode) {
		logger.info("CustomerServiceImpl.verifyAccount: " + verificationCode);
		Users user = userDao.findUserByVerificationCode(verificationCode);

		if (user == null || user.isEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			userDao.saveCustomer(user);
			return true;
		}
	}

	void initDataDefaultCustomer(Users customer) {

		String email = customer.getEmail();
		int index = email.indexOf('@');
		String username = email.substring(0, index);
		customer.setUsername(username);
		customer.setEmail(email);
		customer.setPassword(encodePassword(customer.getPassword()));

		customer.setCreatedAt(new Date());
		customer.setCreatedBy("Client");
		customer.setUpdatedAt(new Date());
		customer.setUpdatedBy("Client");
		customer.setStatus(false);
		customer.setEnabled(false);

		String randomCode = RandomString.make(64);
		customer.setVerificationCode(randomCode);

	}

	UsersRoles initRoleDefaultCustomer(Users customer) {
		UsersRoles userRole = new UsersRoles();
		int roleId = 1;

		List<Users> user = userDao.findUserByUsernameAndByStatus(customer.getUsername(), SystemConstant.INACTIVE_ACCOUNT);
		userRole.setUserId(user.get(0).getUserId());
		userRole.setRoleId(roleId);
		userRole.setEnabled(true);
		return userRole;
	}

	public List<Users> findCustomerByUsernameAndPassword(Login login) {
		logger.info("CustomerServiceImpl.findCustomerByUsernameAndPassword");
		List<Users> listUser = userDao.findUserByUsernameAndByStatus(login.getUsername(),
				SystemConstant.ACTIVE_ACCOUNT);
		if (CollectionUtils.isEmpty(listUser)) {
			return null;
		} else {
			boolean checkpw = userPasswordCheck(login.getPassword(), listUser.get(0));
			if (checkpw) {
				return listUser;
			} else {
				return null;
			}
		}
	}

	public boolean userPasswordCheck(String password, Users user) {
		String encodedPassword = user.getPassword();
		return passwordEncoder.matches(password, encodedPassword);
	}

	public List<UserInfo> getAccounts() {
		logger.info("CustomerServiceImpl.getAccounts");
		List<Users> listUser = userDao.getListAccount();
		if (CollectionUtils.isEmpty(listUser)) {
			return null;
		} else {
			return cvtUserInfoDto(listUser);
		}
	}

	private List<UserInfo> cvtUserInfoDto(List<Users> listUser) {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		UserInfo item;
		for (Users u : listUser) {
			item = new UserInfo();
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

	public Page<UserInfo> findPaginated(Pageable pageable) {
		logger.info("CustomerServiceImpl.findPaginated");
		Page<UserInfo> pageUserInfo = userDao.pagingOfUser(pageable);
		return pageUserInfo;
	}

	public List<Users> getInfoUserById(Integer user_id) {
		logger.info("CustomerServiceImpl.getInfoUserById");
		List<Users> listUser = userDao.getAccountById(user_id);
		if (CollectionUtils.isEmpty(listUser)) {
			return null;
		} else {
			return listUser;
		}
	}

	public Users updateInfoUser(Users user) {
		logger.info("CustomerServiceImpl.updateInfoUser");
		return userDao.updateInfoCustomer(user);
	}

}
