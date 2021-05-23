package com.poly.controllers;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.poly.entities.Restaurant;
import com.poly.services.RecoverPasswordService;
import com.poly.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomepageController {

	private final RestaurantService restaurantService;
	private final RecoverPasswordService recoverPasswordService;

	@GetMapping(value = "index")
	public ModelAndView home() {
		ModelAndView model = new ModelAndView("/index");
		return model;
	}

	@GetMapping(value = "login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@GetMapping("forgot")
	public ModelAndView forgotView() {
		return new ModelAndView("forgot_password");
	}

	@PostMapping("forgot")
	public ResponseEntity<Object> forgot(@RequestBody String email) throws JSONException {
		if (!recoverPasswordService.forgotPassword(email)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("recover")
	public ModelAndView recoverView() {
		return new ModelAndView("recover_password");
	}

	@PostMapping("recover")
	public ResponseEntity<Object> recover(@RequestBody String request) throws JSONException {
		JSONObject requestJSON = new JSONObject(request);

		switch (recoverPasswordService.recoverPassword(requestJSON.getString("token"),
				requestJSON.getString("password"))) {
		case 0:
			return ResponseEntity.notFound().build();
		case 1:
			return ResponseEntity.status(HttpStatus.GONE).build();
		case 2:
			return ResponseEntity.ok().build();
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(value = "topRestaurant")
	public ResponseEntity<List<Restaurant>> topRestaurantList() {
		return ResponseEntity.ok(restaurantService.listTopOrderedRestaurant(6));
	}
}
