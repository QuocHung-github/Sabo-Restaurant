package com.poly.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.poly.entities.RecoverPassword;
import com.poly.entities.UserAccount;
import com.poly.repositories.RecoverPasswordRepository;
import com.poly.repositories.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecoverPasswordService {
	private final JavaMailSender javaMail;
	private final RecoverPasswordRepository recoverRepository;
	private final UserAccountRepository accountRepository;
	private final BCryptPasswordEncoder pwdEncoder;

	@Transactional
	public boolean forgotPassword(String email) {
		Optional<UserAccount> check = accountRepository.findByRoleIdAndUserEmail(2, email);

		if (check.isEmpty())
			return false;

		RecoverPassword recover = new RecoverPassword();
		recover.setAccount(check.get());
		recover.setToken(UUID.randomUUID());
		recover.setCreatedDate(LocalDateTime.now());
		recover.setState(false);

		recoverRepository.invalidateAllTokens();

		RecoverPassword added = recoverRepository.save(recover);
		String message = String.format(
				"Cảm ơn quý khách đã quan tâm đến dịch vụ của chúng tôi. Để đổi mật khẩu, vui lòng click vào đường link dưới đây: "
						+ ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
						+ "/recover?token=%s",
				added.getToken().toString());

		simpleMessage(added.getAccount().getUser().getEmail(), "SaBo Restaurant - Đổi mật khẩu", message);

		return true;
	}

	@Transactional
	public int recoverPassword(String token, String password) {
		Optional<RecoverPassword> check = recoverRepository.findByToken(UUID.fromString(token));

		if (check.isEmpty())
			return 0;

		RecoverPassword requested = check.get();

		if (requested.isState() || requested.getCreatedDate().until(LocalDateTime.now(), ChronoUnit.HOURS) >= 2) {
			recoverRepository.invalidateToken(requested.getId());
			return 1;
		}

		UserAccount update = requested.getAccount();
		update.setPassword(pwdEncoder.encode(password));

		accountRepository.save(update);

		recoverRepository.invalidateToken(requested.getId());

		return 2;
	}

	public void simpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("danhntps09651@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		javaMail.send(message);
	}
}
