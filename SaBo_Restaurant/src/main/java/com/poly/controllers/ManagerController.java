package com.poly.controllers;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.entities.Booking;
import com.poly.entities.Restaurant;
import com.poly.entities.User;
import com.poly.entities.UserAccount;
import com.poly.services.ManagerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/manager/")
@RequiredArgsConstructor
public class ManagerController {

	private final ManagerService managerService;

	@GetMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("/dashboard/webadmin");
		mav.addObject("stats", managerService.statistic());
		return mav;
	}

	@GetMapping("customer")
	public ModelAndView customer() {
		return new ModelAndView("/dashboard/pages/manager/customer");
	}

	@GetMapping("merchant")
	public ModelAndView merchant() {
		return new ModelAndView("/dashboard/pages/manager/merchant");
	}

	@GetMapping("stats")
	public ModelAndView stats() {
		return new ModelAndView("/dashboard/pages/manager/stats");
	}

	@PostMapping("user")
	public ResponseEntity<Object> currentUser() throws AccessDeniedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			throw new AccessDeniedException("Bạn không có quyền thực hiện tác vụ này");

		User current = managerService.managerInfo(auth.getName());

		return ResponseEntity.ok(current);
	}

	@PostMapping("bookingList")
	public ResponseEntity<Object> bookingList() {
		List<Booking> bookingList = managerService.getBookingList();
		if (bookingList.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(bookingList);
	}

	@PostMapping("customerList")
	public ResponseEntity<Object> getCustomerList() {
		List<User> customers = managerService.getCustomerList();

		if (!customers.isEmpty()) {
			return ResponseEntity.ok(customers);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chưa tồn tại khách hàng nào trong hệ thống");
	}

	@PutMapping("customerInfo")
	public ResponseEntity<Object> updateCustomerAccount(@RequestBody UserAccount account) {
		UserAccount updated = managerService.updateUser(account);
		if (updated != null)
			return ResponseEntity.ok(updated);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy user cần cập nhật");
	}

	@PostMapping("restaurantList")
	public ResponseEntity<Object> getMerchantList() {
		List<Restaurant> merchants = managerService.getRestaurantList();

		if (!merchants.isEmpty()) {
			return ResponseEntity.ok(merchants);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chưa tồn tại chủ cửa hàng nào trong hệ thống");
	}

	@PostMapping(value = "restaurantRegister", consumes = { "multipart/form-data" })
	public ResponseEntity<Object> merchantRegister(@RequestPart(name = "restaurant") String restaurantJSON,
			@RequestPart(name = "image", required = false) MultipartFile image) {
		try {
			ObjectMapper objMapper = new ObjectMapper();
			Restaurant restaurant = objMapper.readValue(restaurantJSON, Restaurant.class);

			managerService.registerRestaurant(restaurant, image);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping(value = "restaurantUpdate", consumes = { "multipart/form-data" })
	public ResponseEntity<Object> merchantUpdate(@RequestPart(name = "restaurant") String restaurantJSON,
			@RequestPart(name = "image", required = false) MultipartFile image) {
		try {
			ObjectMapper objMapper = new ObjectMapper();
			Restaurant restaurant = objMapper.readValue(restaurantJSON, Restaurant.class);

			managerService.updateRestaurant(restaurant, image);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
