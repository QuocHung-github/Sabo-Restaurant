package com.poly.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.poly.entities.Restaurant;
import com.poly.repositories.FoodRepository;
import com.poly.repositories.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantService {
	private final RestaurantRepository restaurantRepo;
	
	public List<Restaurant> listTopOrderedRestaurant(int limit) {
		return restaurantRepo.findTopRestaurantByBooking(PageRequest.of(0, limit));
	}
	
	public List<Restaurant> searchRestaurantByName(String restaurantName) {
		return restaurantRepo.findAllByRestaurantNameContains(restaurantName);
	}
	
	public List<Restaurant> listAll() {
		return restaurantRepo.findAll();
	}
	
	public Optional<Restaurant> getRestaurantById(int id) {
		return restaurantRepo.findById(id);
	}
}
