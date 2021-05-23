package com.poly.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entities.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
	public Optional<UserAccount> findByUsername(String username);
	public Optional<UserAccount> findByRoleIdAndUserEmail(int roleId, String email);
		
	@Query(value = "SELECT ua FROM UserAccount ua "
			+ "WHERE ua.username = :#{#userAccount.username} "
			+ "AND ua.active = 1")
	UserAccount findActiveCustomerAccount(@Param("userAccount") UserAccount userAccount);
	
	@Query(value = "SELECT ua FROM UserAccount ua "
			+ "WHERE ua.id = :#{#userAccount.id} "
			+ "AND ua.username = :#{#userAccount.username} ")
	Optional<UserAccount> findCustomerAccount(@Param("userAccount") UserAccount userAccount);
		
	@Query(value = "UPDATE UserAccount ua "
			+ "SET ua.active = 0 "
			+ "WHERE ua.id = :id")
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	int deleteAcount(int id);
	
	long countByRoleId(int roleId);
}
