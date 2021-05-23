package com.poly.config;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.EventListener;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.poly.services.UserCredentialService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class GlobalSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final AccessRedirectEntryPoint entryPoint;
	private final UserCredentialService userCredentialService;


	@Bean
	public SecureRandom secureRandom() {
		return new SecureRandom();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(BCryptVersion.$2B, 12, secureRandom());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userCredentialService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
    public static ServletListenerRegistrationBean<EventListener> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<EventListener>(new HttpSessionEventPublisher());
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**", "/css/**", "/img/**", "/images/**", "/webjars/**", "/fonts/**", "/dist/**", "/assets/**",
				"/vendors/**", "/avatar/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/manager/*").hasRole("ADMIN")
				.and().authorizeRequests().antMatchers("/customer/register").permitAll().antMatchers("/customer/*").hasAnyRole("CUSTOMER", "MERCHANT", "ADMIN")
				.and().authorizeRequests().antMatchers("/merchant/register").permitAll().antMatchers("/merchant/*").hasRole("MERCHANT")

				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login_processing")
				.defaultSuccessUrl("/")//.successHandler(loginSuccessHandler)
				.failureUrl("/login?status=401").permitAll()

				.and()
				.logout()
				.logoutUrl("/logout")
				.permitAll()
				.logoutSuccessUrl("/index")
				.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID")
				.permitAll()
				
				.and()
				.httpBasic()
				
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(entryPoint)
				
				.and()
				.sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	            .sessionFixation()
	            .migrateSession()
				.invalidSessionUrl("/login?status=401")
				.maximumSessions(1)
				.expiredUrl("/login?status=401")
				.maxSessionsPreventsLogin(true)
				
				;

		//http.cors().configurationSource(corsConfigurationSource());
		http.cors().disable().csrf().disable();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8440"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
