package com.poly.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
	
	@Query(value = "SELECT r FROM Booking b "
			+ "JOIN b.restaurant r "
			+ "GROUP BY r.id, r.restaurantName, r.email, r.phone, r.address, r.openTime, r.closeTime, r.state, r.overview, r.bookedTimes, r.logo "
			+ "ORDER BY COUNT(b.id) DESC")
	public List<Restaurant> findTopRestaurantByBooking(Pageable limit);
	
	@Query(value = "SELECT r FROM Restaurant r "
			+ "WHERE r.email = :#{#restaurant.email} "
			+ "AND r.phone = :#{#restaurant.phone} ")
	public Restaurant findRestaurant(@Param("restaurant") Restaurant restaurant);
	
	public List<Restaurant> findAllByRestaurantNameContains(String restaurantName);
}
