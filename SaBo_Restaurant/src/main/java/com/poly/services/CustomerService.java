package com.poly.services;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.entities.User;
import com.poly.entities.UserAccount;
import com.poly.repositories.UserAccountRepository;
import com.poly.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final UserRepository userRepository;
	private final UserAccountRepository userAccountRepository;
	private final BCryptPasswordEncoder pwdEncoder;

	public Optional<User> customerInfo(int id) {
		return userRepository.findById(id);
	}
	
	public User customerInfo(String username) throws AccessDeniedException {
		return userAccountRepository.findByUsername(username).get().getUser();
	}

	public User register(User user) {
		UserAccount account = user.getAccount();
		user.setAccount(null);
		account.setUser(user);
		account.setPassword(pwdEncoder.encode(account.getPassword()));

		User check = userRepository.findUser(user);

		if (check == null) {
			User addedUser = userRepository.saveAndFlush(user);
			account.setUser(addedUser);
			userAccountRepository.save(account);
			
			return addedUser;
		}
		
		return null;
	}

	public User updateInfo(User user) {
		if (!user.getAccount().getPassword().isBlank()) {
			user.getAccount().setPassword(pwdEncoder.encode(user.getAccount().getPassword()));
		}
		Optional<User> check = userRepository.findById(user.getId());

		if (check.isEmpty())
			return null;

		User update = check.get();

		update.setFirstName(!user.getFirstName().isBlank() ? user.getFirstName() : update.getFirstName());
		update.setLastName(!user.getLastName().isBlank() ? user.getLastName() : update.getLastName());
		update.setGender(user.getGender());
		update.setBirthday(user.getBirthday() != null ? user.getBirthday() : update.getBirthday());
		update.setEmail(!user.getEmail().isBlank() ? user.getEmail() : update.getEmail());
		update.setPhone(!user.getPhone().isBlank() ? user.getPhone() : update.getPhone());
		update.setAddress(!user.getAddress().isBlank() ? user.getAddress() : update.getAddress());
		
		update.getAccount().setUsername(!user.getAccount().getUsername().isBlank() ? user.getAccount().getUsername() : update.getAccount().getUsername());
		update.getAccount().setPassword(!user.getAccount().getPassword().isBlank() ? user.getAccount().getPassword() : update.getAccount().getPassword());
		
		return userRepository.save(update);
	}

	public UserAccount updateAccount(UserAccount account) {
		if (!account.getPassword().isBlank())
			account.setPassword(pwdEncoder.encode(account.getPassword()));

		Optional<UserAccount> check = userAccountRepository.findCustomerAccount(account);

		if (check.isPresent()) {
			UserAccount update = check.get();

			update.setUsername(!account.getUsername().isBlank() ? account.getUsername() : update.getUsername());
			update.setPassword(!account.getPassword().isBlank() ? account.getPassword() : update.getPassword());

			return userAccountRepository.save(update);
		}
		
		return null;
	}

	@Transactional
	public boolean deleteAccount(int id) {
		return userAccountRepository.deleteAcount(id) > 0 ? true : false;
	}
}
