package com.poly.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MerchantRepository extends UserRepository {
	
	@Query(value = "UPDATE Restaurant r "
			+ "SET r.state = :state "
			+ "WHERE r.id = :id")
	@Modifying
	public int changeRestaurantState(@Param("state") int state, @Param("id") int id);
	
	
}
