package com.poly.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	// Cấu hình để sử dụng các file nguồn tĩnh (html, image, ..)
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/avatar/**").addResourceLocations("file:///D:/Images/Avatar");
		registry.addResourceHandler("/images/restaurant/**").addResourceLocations("file:///D:/Images/Restaurant/");
		registry.addResourceHandler("/images/food/**").addResourceLocations("file:///D:/Images/Food/");
	}
}
