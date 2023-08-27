package com.thuannluit.pethouse.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.thuannluit.pethouse.entity.Roles;
import com.thuannluit.pethouse.entity.Users;

public class PetHouseUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Users user;
	
	public PetHouseUserDetails(Users u) {
		this.user = u;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Roles> roles = user.getRoles();
		
		List<SimpleGrantedAuthority> authories = new ArrayList<SimpleGrantedAuthority>();
		
		for (Roles role : roles) {
			authories.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		
		return authories;
	}

	public String getPassword() {
		return user.getPassword();  
	}

	public String getUsername() {
		return user.getUsername();  
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
