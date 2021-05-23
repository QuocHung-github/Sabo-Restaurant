package com.poly.services;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.poly.entities.Booking;
import com.poly.entities.Food;
import com.poly.entities.Restaurant;
import com.poly.entities.UserAccount;
import com.poly.repositories.BookingRepository;
import com.poly.repositories.FoodRepository;
import com.poly.repositories.MerchantRepository;
import com.poly.repositories.RestaurantRepository;
import com.poly.repositories.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MerchantService {
	private final MerchantRepository merchantRepository;
	private final RestaurantRepository restaurantRepository;
	private final UserAccountRepository userAccountRepository;
	private final FoodRepository foodRepository;
	private final BookingRepository bookingRepository;
	
	@Transactional
	public Optional<Restaurant> changeState(int state, int id) {
		merchantRepository.changeRestaurantState(state, id);
		
		return restaurantRepository.findById(id);
	}
	
	public UserAccount getMerchantInfo(String username) throws AccessDeniedException {
		return userAccountRepository.findByUsername(username).get();
	}
	
	public List<Booking> getBookingListByUsername(String username) {
		return bookingRepository.findByRestaurantManagerUsername(username);
	}
	
	public Booking verifyBooking(Booking booking) {
		return bookingRepository.save(booking);
	}
	
	public List<Food> getMenu(int restaurantId) {
		return foodRepository.findByRestaurantId(restaurantId);
	}
	
	public void addMenu(Food food) {
		foodRepository.save(food);
	}
	
	public void updateMenu(Food food) {
		Optional<Food> foodopt = foodRepository.findById(food.getId());
		
		if(foodopt.isPresent()) {
			Food update = foodopt.get();
			
			update.setName(food.getName() != null && !food.getName().isBlank() ? food.getName() : update.getName());
			update.setPrice(food.getPrice());
			update.setType(food.getType() != null ? food.getType() : update.getType());
			
			foodRepository.save(update);
		}
	}
}
