package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpFilter;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private OauthAuthenticationSuccessHandler handler;

//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user=User.builder()
//				.username("user")
//				.password("{noop}user")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user);
//	}
	
	@Bean public SecurityFilterChain filter(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.authorizeHttpRequests(request->{
			request.requestMatchers("/user/**").authenticated()
			.anyRequest().permitAll();
		});
		
		httpSecurity.formLogin(form -> {
	        form.loginPage("/login")  // Custom login page
	            .loginProcessingUrl("/validate")  // URL for login form submission
	            .defaultSuccessUrl("/user/dashboard", true)  // Redirect after successful login
	            .failureUrl("/login?error=true")  // Redirect on login failure
	            .usernameParameter("name")  // Name of username field in form
	            .passwordParameter("password");  // Name of password field in form
	    });
		
		httpSecurity.oauth2Login(oauth->{
			oauth.loginPage("/login");
			oauth.successHandler(handler);
		});
//		
//		httpSecurity.oauth2Login(Customizer.withDefaults());
		
		httpSecurity.csrf().disable();
		
		httpSecurity.logout(logout->
		logout.logoutUrl("/do-logout")
		);
		
		return httpSecurity.build();
	}
	
	@Bean public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
		dao.setPasswordEncoder(encoder());
		dao.setUserDetailsService(customUserDetailsService);
		return dao;
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
