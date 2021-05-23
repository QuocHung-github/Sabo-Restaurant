package com.poly.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer>{
	
	@Query(value = "SELECT b FROM Booking b "
			+ "JOIN FETCH b.user u "
			+ "WHERE u.id = :custId")
	public List<Booking> findByCustomerId(@Param("custId") int id);
	
	@Query(value = "SELECT b FROM Booking b "
			+ "JOIN FETCH b.user u "
			+ "JOIN FETCH u.account ac "
			+ "WHERE ac.username = :username")
	public List<Booking> findByCustomerUsername(@Param("username") String username);
	
	public Booking findByCreatedDateAndBookingDate(LocalDateTime createdDate, LocalDateTime bookingDate);
		
	public List<Booking> findByUserAccountUsername(String username);
	
	public List<Booking> findByRestaurantId(int id);
	
	public List<Booking> findByRestaurantManagerUsername(String username);
}
