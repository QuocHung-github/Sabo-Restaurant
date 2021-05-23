package com.poly.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.poly.entities.UserAccount;
import com.poly.repositories.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCredentialService implements UserDetailsService {

	private final UserAccountRepository userAccountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserAccount> account = userAccountRepository.findByUsername(username);

		if (account.isEmpty() || !account.get().isActive()) {
			throw new UsernameNotFoundException("Không tìm thấy tài khoản");
		}

		UserAccount userAccount = account.get();
		
		return User.builder()
            	.username(userAccount.getUsername())
            	.password(userAccount.getPassword())
            	.roles(userAccount.getRole().getRole())
            	.disabled(!userAccount.isActive())
            	.build();
	}

}
