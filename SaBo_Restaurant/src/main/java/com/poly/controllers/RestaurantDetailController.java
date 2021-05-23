package com.poly.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.poly.entities.Restaurant;
import com.poly.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurantinfo/")
@RequiredArgsConstructor
public class RestaurantDetailController {

	private final RestaurantService restaurantService;

	@GetMapping(value = "detail")
	public ModelAndView detail(@RequestParam(name = "id") int id) {
		return new ModelAndView("restaurant");
	}

	@PostMapping(value = "detail")
	public ResponseEntity<Object> restaurantInfo(@RequestParam(name = "id") int id) {
		
		Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
		
		if(restaurant.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nhà hàng đã yêu cầu"); 
		
		return ResponseEntity.ok(restaurant);
	}
}
