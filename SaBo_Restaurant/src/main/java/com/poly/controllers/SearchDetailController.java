package com.poly.controllers;

import java.util.List;

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
@RequestMapping("/search/")
@RequiredArgsConstructor
public class SearchDetailController {

	private final RestaurantService restaurantService;

	@GetMapping(value = "index")
	public ModelAndView search() {
		return new ModelAndView("search");
	}

	@PostMapping(value = "result")
	public ResponseEntity<Object> searchRestaurant(@RequestParam("search") String search) throws Exception {
		List<Restaurant> restaurantList;

		try {
			if (search != null && !search.isBlank()) {
				restaurantList = restaurantService.searchRestaurantByName(search);
			} else {
				restaurantList = restaurantService.listAll();

			}

			return ResponseEntity.ok(restaurantList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
