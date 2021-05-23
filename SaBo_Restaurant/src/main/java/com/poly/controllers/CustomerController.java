package com.poly.controllers;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.codehaus.jettison.json.JSONException;
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
import com.poly.entities.User;
import com.poly.entities.UserAccount;
import com.poly.services.BookingService;
import com.poly.services.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer/")
@RequiredArgsConstructor
public class CustomerController {
	private final CustomerService customerService;
	private final BookingService bookingService;
	
	@GetMapping("index")
	public ModelAndView index() {
		return new ModelAndView("/dashboard/customer");
	}

	@GetMapping(value = "register")
	public ModelAndView customerRegister() {
		return new ModelAndView("/register");
	}

	@PostMapping("user")
	public ResponseEntity<Object> currentUser() throws AccessDeniedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			throw new AccessDeniedException("Bạn không có quyền thực hiện tác vụ này");

		User current = customerService.customerInfo(auth.getName());

		return ResponseEntity.ok(current);
	}

	@PostMapping(value = "register")
	public ResponseEntity<Object> register(@RequestBody User user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(customerService.register(user));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("checkout")
	public ResponseEntity<Object> checkout(@RequestBody Booking booking) throws AccessDeniedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			throw new AccessDeniedException("Bạn không có quyền thực hiện tác vụ này");

		User current = customerService.customerInfo(auth.getName());
		booking.setUser(current);
		
		Booking result = bookingService.checkout(booking);

		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@PostMapping("history")
	public ResponseEntity<Object> history() throws AccessDeniedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			throw new AccessDeniedException("Bạn không có quyền thực hiện tác vụ này");

		String username = auth.getName();

		List<Booking> history = bookingService.historyByCustomerUsername(username);

		return ResponseEntity.ok(history);
	}

	@PutMapping("updateInfo")
	public ResponseEntity<Object> updateInfo(@RequestBody User user) throws IOException, JSONException {
		User updated = customerService.updateInfo(user);
		if (updated != null) return ResponseEntity.ok(updated);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy user cần cập nhật");
	}

	@DeleteMapping("delete")
	public ResponseEntity<Object> deleteAccount(@RequestParam(value = "id") int id) {
		if (customerService.deleteAccount(id))
			return ResponseEntity.ok().build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

}
