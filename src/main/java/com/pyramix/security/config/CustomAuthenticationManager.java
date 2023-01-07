package com.pyramix.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.pyramix.security.service.UserDetailsServiceImpl;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		final UserDetails userDetails =
				getUserDetailsServiceImpl().loadUserByUsername(auth.getName());
		if (!passwordEncoder().matches(auth.getCredentials().toString(), userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Login");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), 
				userDetails.getPassword(), 
				userDetails.getAuthorities());
	}

	public UserDetailsServiceImpl getUserDetailsServiceImpl() {
		return userDetailsServiceImpl;
	}

	public void setUserDetailsServiceImpl(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

}
