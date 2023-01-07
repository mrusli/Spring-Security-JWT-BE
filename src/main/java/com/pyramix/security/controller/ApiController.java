package com.pyramix.security.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pyramix.security.config.CustomAuthenticationManager;
import com.pyramix.security.domain.user.User;
import com.pyramix.security.persistence.user.dao.UserDao;
import com.pyramix.security.service.TokenService;

@RestController
public class ApiController {
	
	@Autowired
	private CustomAuthenticationManager authenticationManager;
	
	@Autowired
	private UserDao userDao;
	
	private final TokenService tokenService;
	
	private static final Logger log = Logger.getLogger(ApiController.class);

	public ApiController(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@PostMapping("/jwtlogin")
	public ResponseEntity<?> login(@RequestBody User user) throws Exception {
		log.info("RequestBody : "+user.toString());

		final Authentication authentication =
				getAuthenticationManager().authenticate(
						new UsernamePasswordAuthenticationToken(
			                    user.getUser_name(), user.getUser_password()));
		
		// if authentication succeeded and is not anonymous
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
        	log.info("authentication: "+authentication.toString());
        	
        	// User authUser = getUserDao().findUserByUsername(authentication.getName());
        	
            // set authentication in security context holder
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // generate new JWT token
            String jwtToken = getTokenService().generateToken(authentication);
            
            // return response containing the JWT token
            return ResponseEntity.ok()
            		.header(HttpHeaders.AUTHORIZATION, jwtToken)
            		.body(user.toString());
        }		
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	public CustomAuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(CustomAuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public TokenService getTokenService() {
		return tokenService;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
