package com.pyramix.security.persistence.user.dao;

import java.util.List;

import com.pyramix.security.domain.user.User;
import com.pyramix.security.domain.user.UserRole;

public interface UserDao {

	/**
	 * Find the user authorized to use the system, passing in the id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public User findUserById(Long id) throws Exception;
	
	/**
	 * Find the user authorized to use the system, passing in the username
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User findUserByUsername(String username) throws Exception;
	
	/**
	 * All users authorized to use the system
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<User> findAllUsers() throws Exception;
	
	/**
	 * Save the new user, including the password, to the database
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Long save(User user) throws Exception;
	
	/**
	 * Update the existing user, including the password and roles
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void update(User user) throws Exception;
	
	/**
	 * Users who has the userRole ({@link UserRole})
	 * 
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	public List<User> findUsersByUserRole(UserRole userRole) throws Exception;
}
