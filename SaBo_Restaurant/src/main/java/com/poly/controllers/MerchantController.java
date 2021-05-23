package com.poly.controllers;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.poly.entities.Booking;
import com.poly.entities.Food;
import com.poly.entities.Restaurant;
import com.poly.entities.UserAccount;
import com.poly.repositories.FoodRepository;
import com.poly.services.MerchantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/merchant/")
@RequiredArgsConstructor
public class MerchantController {
	private final MerchantService merchantService;
	private final FoodRepository foodRepository;

	@GetMapping("index")
	public ModelAndView test() {
		return new ModelAndView("/dashboard/merchant");
	}
	
	@GetMapping("manage")
	public ModelAndView manage() {
		return new ModelAndView("/dashboard/pages/merchant/manage");
	}
	
	@GetMapping("stats")
	public ModelAndView stats() {
		return new ModelAndView("/dashboard/pages/merchant/stats");
	}
	
	@GetMapping("menu")
	public ResponseEntity<Object> getMenu() throws AccessDeniedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			throw new AccessDeniedException("Bạn không có quyền thực hiện tác vụ này");
		
		UserAccount merchant = merchantService.getMerchantInfo(auth.getName());
		return ResponseEntity.ok(merchant.getRestaurant().getMenu());
	}
	
	@GetMapping("restaurant")
	public ResponseEntity<Object> restaurant() throws AccessDeniedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			throw new AccessDeniedException("Bạn không có quyền thực hiện tác vụ này");
		
		UserAccount merchant = merchantService.getMerchantInfo(auth.getName());
		return ResponseEntity.ok(merchant.getRestaurant());
	}
	
	@PostMapping("booking")
	public ResponseEntity<Object> bookingList() throws AccessDeniedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			throw new AccessDeniedException("Bạn không có quyền thực hiện tác vụ này");
		
		List<Booking> list = merchantService.getBookingListByUsername(auth.getName());
		
//		if(!list.isEmpty()) return ResponseEntity.ok(list);
//		return ResponseEntity.notFound().build();
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("user")
	public ResponseEntity<Object> currentMerchant() throws AccessDeniedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) throw new AccessDeniedException("Bạn không có quyền thực hiện tác vụ này");
		
		UserAccount current = merchantService.getMerchantInfo(auth.getName());
		return ResponseEntity.ok(current);
	}

	@PostMapping("state")
	public ResponseEntity<Object> restaurantState(@RequestParam("state") int state, @RequestParam("id") int id) {
		Optional<Restaurant> res = merchantService.changeState(state, id);

//		if (res.isPresent()) {
//			return ResponseEntity.ok(res.get());
//		}
//
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(res.get());
	}
	
	@PostMapping("verifyBooking")
	public ResponseEntity<Object> verifyBooking(@RequestBody Booking booking) {
		Booking result = merchantService.verifyBooking(booking);
		return ResponseEntity.ok().body(result);
	}

	@PostMapping("menu")
	public ResponseEntity<Object> addMenuItem(@RequestBody Food food) {
		merchantService.addMenu(food);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PutMapping("menu")
	public ResponseEntity<Object> updateMenuItem(@RequestBody Food food) {
		merchantService.updateMenu(food);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("menu")
	public ResponseEntity<Object> deleteMenuItem(@RequestBody Food food) {
		foodRepository.delete(food);
		return ResponseEntity.ok().build();
	}
}
