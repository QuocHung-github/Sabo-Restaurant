package com.poly.config;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AccessRedirectEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		if(response.isCommitted()) throw new IOException();
		
		String requestedWith = request.getHeader("X-Requested-With") != null ? request.getHeader("X-Requested-With")
				: "";
		String requestURI = request.getRequestURI();		
		
		
		if (requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
//			if (requestURI.contains("/manager/")) {
//				response.sendError(HttpStatus.UNAUTHORIZED.value(), "/manager/login?status=401");
//			} else if (requestURI.contains("/merchant/")) {
//				response.sendError(HttpStatus.UNAUTHORIZED.value(), "/merchant/login?status=401");
//			} else {
//				//response.sendError(HttpStatus.UNAUTHORIZED.value(), "/customer/login?status=401");
//				response.sendError(HttpStatus.UNAUTHORIZED.value(), "/index");
//			}
			
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "/login?status=401");
		} else {
//			if (requestURI.contains("/manager/")) {
//				response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
//				response.sendRedirect("/manager/login?status=401");
//			} else if (requestURI.contains("/merchant/")) {
//				response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
//				response.sendRedirect("/merchant/login?status=401");
//			} else {
//				response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
//				//response.sendRedirect("/customer/login?status=401");
//				response.sendRedirect("/index");
//			}
			
			response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
			response.sendRedirect("/login?status=401");
		}

	}
}
