package com.poly.services;

import java.nio.file.AccessDeniedException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poly.entities.Booking;
import com.poly.entities.Restaurant;
import com.poly.entities.User;
import com.poly.entities.UserAccount;
import com.poly.repositories.BookingRepository;
import com.poly.repositories.ManagerRepository;
import com.poly.repositories.RestaurantRepository;
import com.poly.repositories.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerService {
	private final ManagerRepository managerRepository;
	private final UserAccountRepository userAccountRepository;
	private final RestaurantRepository restaurantRepository;
	private final BookingRepository bookingRepository;

	private final BCryptPasswordEncoder pwdEncoder;

	public List<Booking> getBookingList() {
		return bookingRepository.findAll();
	}

	public List<User> getCustomerList() {
		return managerRepository.findByAccountRoleId(2);
	}

	public List<Restaurant> getRestaurantList() {
		return restaurantRepository.findAll();
	}

	public User managerInfo(String username) throws AccessDeniedException {
		return userAccountRepository.findByUsername(username).get().getUser();
	}

	public void registerRestaurant(Restaurant restaurant, MultipartFile image) throws Exception {
		UserAccount account = restaurant.getManager();
		restaurant.setManager(null);
		account.setPassword(pwdEncoder.encode(account.getPassword()));
		Restaurant check = restaurantRepository.findRestaurant(restaurant);

		if (check == null) {
			Restaurant addedRestaurant = restaurantRepository.save(restaurant);
			account.setRestaurant(addedRestaurant);

			userAccountRepository.save(account);

			if (image != null && image.getSize() > 0)
				Files.copy(image.getInputStream(), Paths.get("/images/restaurant/" + image.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public UserAccount updateUser(UserAccount account) {
		account.setPassword(pwdEncoder.encode(account.getPassword()));
		Optional<UserAccount> check = userAccountRepository.findById(account.getId());

		if (check.isPresent()) {
			UserAccount update = check.get();
			update.setUsername(account.getUsername().isBlank() ? account.getUsername() : update.getUsername());
			update.setPassword(account.getPassword().isBlank() ? account.getPassword() : update.getPassword());
			update.setActive(account.isActive());
			return userAccountRepository.save(update);
		}

		return null;
	}

	public void updateRestaurant(Restaurant restaurant, MultipartFile image) throws Exception {
		if (!restaurant.getManager().getPassword().isBlank())
			restaurant.getManager().setPassword(pwdEncoder.encode(restaurant.getManager().getPassword()));

		Optional<Restaurant> resOpt = restaurantRepository.findById(restaurant.getId());

		if (resOpt.isPresent()) {
			Restaurant update = resOpt.get();

			update.setRestaurantName(!restaurant.getRestaurantName().isBlank() ? restaurant.getRestaurantName()
					: update.getRestaurantName());
			update.setEmail(!restaurant.getEmail().isBlank() ? restaurant.getEmail() : update.getEmail());
			update.setPhone(!restaurant.getPhone().isBlank() ? restaurant.getPhone() : update.getPhone());
			update.setAddress(!restaurant.getAddress().isBlank() ? restaurant.getAddress() : update.getAddress());
			update.setOpenTime(restaurant.getOpenTime() != null ? restaurant.getOpenTime() : update.getOpenTime());
			update.setCloseTime(restaurant.getCloseTime() != null ? restaurant.getCloseTime() : update.getCloseTime());
			update.setOverview(!restaurant.getOverview().isBlank() ? restaurant.getOverview() : update.getOverview());

			update.getManager().setUsername(
					!restaurant.getManager().getUsername().isBlank() ? restaurant.getManager().getUsername()
							: update.getManager().getUsername());
			update.getManager().setPassword(
					!restaurant.getManager().getPassword().isBlank() ? restaurant.getManager().getPassword()
							: update.getManager().getPassword());

			restaurantRepository.save(update);

			if (image != null && image.getSize() > 0)
				Files.copy(image.getInputStream(), Paths.get("/images/restaurant/" + image.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
		}
	}

}
