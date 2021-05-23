package com.poly.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entities.Food;
import com.poly.entities.Restaurant;

public interface FoodRepository extends JpaRepository<Food, Integer> {
	public List<Food> findByRestaurant(Restaurant restaurant);
	public List<Food> findByRestaurantId(int id);
	
//	@Query(value = "SELECT b FROM Booking b "
//			+ "JOIN Food f "
//			+ "ORDER BY COUNT(b.id) DESC")
//	public List<Food> findTopFoodByBooking(Pageable limit);
	
	
}
