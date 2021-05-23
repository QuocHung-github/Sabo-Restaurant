package com.poly.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		authorities.forEach(authority -> {
			if (authority.getAuthority().equals("ROLE_ADMIN")) {
				try {
					redirectStrategy.sendRedirect(request, response, "/manager/index");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (authority.getAuthority().equals("ROLE_MERCHANT")) {
				try {
					redirectStrategy.sendRedirect(request, response, "/merchant/index");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (authority.getAuthority().equals("ROLE_CUSTOMER")) {
				try {
					redirectStrategy.sendRedirect(request, response, "/index");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				throw new IllegalStateException();
			}
		});

	}

}
