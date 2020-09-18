package com.cts.gateway.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * AuthUserDetails
 * @author Arkaprabha
 *
 */
public class AuthUserDetails implements UserDetails{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Authentication user
	 */
	private final transient AuthUser authUser;
	
	/**
	 * 
	 * @param authUser
	 */
	public AuthUserDetails(final AuthUser authUser) {
		super();
		this.authUser = authUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(this.authUser.getRoles().split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.authUser.getPassword();
	}

	@Override
	public String getUsername() {
		return this.authUser.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.authUser.isActive();
	}

}
