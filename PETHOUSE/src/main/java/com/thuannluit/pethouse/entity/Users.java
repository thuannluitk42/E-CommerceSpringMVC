package com.thuannluit.pethouse.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class Users extends BasedEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId")
	private int userId;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "fullName", nullable = true)
	private String fullName;
	@Column(name = "birthday", nullable = true)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String birthday;
	@Column(name = "address", nullable = true)
	private String address;
	@Column(name = "email")
	private String email;
	@Column(name = "phoneNumber", nullable = true)
	private String phoneNumber;
	@Column(name = "gender", nullable = true)
	private String gender;
	@Column(name = "verificationCode", length = 64, nullable = true)
	private String verificationCode;
	@Column(name = "status", nullable = true)
	protected boolean status;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", 
			   joinColumns = { @JoinColumn(name = "userId", referencedColumnName = "userId") }, 
			   inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "roleId") })
	@JsonIgnore
	private Set<Roles> roles = new HashSet<Roles>();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", username=" + username + ", password=" + password + ", fullName="
				+ fullName + ", birthday=" + birthday + ", address=" + address + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", gender=" + gender + ", verificationCode=" + verificationCode + ", status=" + status
				+ ", roles=" + roles + "]";
	}

}
