package com.pyramix.security.service;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pyramix.security.config.UserSecurityDetails;
import com.pyramix.security.domain.user.User;
import com.pyramix.security.persistence.user.dao.UserDao;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserDao userDao;
	
	private final Logger log = Logger.getLogger(UserSecurityDetails.class);	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		
		try {
			log.info("---Find User by Username : "+username);
			
			user = getUserDao().findUserByUsername(username);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new UserSecurityDetails(user);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	
}
