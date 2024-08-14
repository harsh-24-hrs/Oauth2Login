package com.scm.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.helper.Helper;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//user dashboard
	@GetMapping("/dashboard")
	public String userDashboard() {
		return "user/dashboard";
	}
	
	@GetMapping("/profile")
	public String userProfile(Authentication authentication) {
		//fetch username of logged user
		String email=Helper.getEmailOfLoggedUser(authentication);
		
		//perform operation on fetched user
		return "user/profile";
	}
	
	//add contacts
	//update contacts
	//delete contacts
}
