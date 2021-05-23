package com.poly.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entities.Food;
import com.poly.entities.Restaurant;

public interface FoodRepository extends JpaRepository<Food, Integer> {
	public List<Food> findByRestaurant(Restaurant restaurant);
	public List<Food> findByRestaurantId(int id);	
}
