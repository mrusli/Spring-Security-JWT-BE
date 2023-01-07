package com.pyramix.security.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.pyramix.security.domain.user.User;

@Repository
public class UserRepository {

	// ref: https://www.baeldung.com/hibernate-entitymanager
	// use PersistenceContext instead of Autowired
	@PersistenceContext
	private EntityManager entityManager;

	public List<User> findAll() {
		String jpql = "SELECT u FROM User u";
		TypedQuery<User> query = getEntityManager().createQuery(jpql, User.class);
		
		return query.getResultList();
	}
	
	public User findUserByUserName(String userName) {
		String jpql = "SELECT u FROM User u WHERE u.user_name = ?1";
		TypedQuery<User> query = getEntityManager().createQuery(jpql, User.class);
		query.setParameter(1, userName);
		
		return query.getResultList().isEmpty() ? null : query.getResultList().get(0);
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
