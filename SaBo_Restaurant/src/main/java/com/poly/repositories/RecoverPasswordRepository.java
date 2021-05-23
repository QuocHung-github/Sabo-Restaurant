package com.poly.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.poly.entities.RecoverPassword;

public interface RecoverPasswordRepository extends JpaRepository<RecoverPassword, Integer> {
	@Query(value = "UPDATE RecoverPassword "
			+ "SET state = 1")
	@Modifying
	int invalidateAllTokens();
	
	@Query(value = "UPDATE RecoverPassword "
			+ "SET state = 1 "
			+ "WHERE id = :id")
	@Modifying
	int invalidateToken(int id);
	
	Optional<RecoverPassword> findByToken(UUID token);
}
