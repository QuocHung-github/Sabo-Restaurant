package com.poly.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByAccountRoleId(int id);
	
	@Query(value = "SELECT u FROM User u "
			+ "WHERE u.email = :#{#user.email} "
			+ "AND u.phone = :#{#user.phone} "
			//+ "AND u.account.username = :#{#user.account.username} "
			+ "AND u.account.active = 1")
	User findUser(@Param("user") User user);
}
