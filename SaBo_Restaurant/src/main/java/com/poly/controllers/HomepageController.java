package com.poly.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.poly.entities.Restaurant;
import com.poly.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomepageController {

	private final RestaurantService restaurantService;

	@GetMapping(value = "index")
	public ModelAndView home() {
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView model = new ModelAndView("/index");
		//String referrer = request.getHeader(HttpHeaders.REFERER);
//		String referrer = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
//
//		if (referrer != null) {
//			request.getSession().setAttribute("url_prior_login", referrer);
//		}
		return model;
	}

	@GetMapping(value = "login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

//	@GetMapping(value = "topFood")
//	public List<Food> topFoodList() {
//		return foodService.listTopOrderedFood(6);
//	}
//
	@PostMapping(value = "topRestaurant")
	public ResponseEntity<List<Restaurant>> topRestaurantList() {
		return ResponseEntity.ok(restaurantService.listTopOrderedRestaurant(6));
	}
}
