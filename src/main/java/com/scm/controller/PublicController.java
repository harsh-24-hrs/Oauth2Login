package com.scm.controller;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.User;
import com.scm.form.UserForm;
import com.scm.repo.UserRepo;
import com.scm.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PublicController {
	
	@Autowired
	private UserService service;

	@GetMapping("/")
	public String home() {
		return "redirect:/home";
	}
	
    @GetMapping("/home")
    public String greeting(Model model){
        System.out.println("home page handler");
        model.addAttribute("name", "Smart contact manager");
        model.addAttribute("github","https://www.github.com/harsh-24-hrs");
        return "home";
    }
    
    @GetMapping("/about")
    public String about(Model model){
        System.out.println("About loading");
        return "about";
    }
    @GetMapping("/services")
    public String services(Model model){
        System.out.println("Services loading");
        return "services";
    }
    
    @GetMapping("/contacts")
    public String contacts(Model model){
        System.out.println("Services loading");
        return "contacts";
    }
    
    @GetMapping("/login")
    public String login(Model model){
        System.out.println("Services loading");
        return "login";
    }
    
    @GetMapping("/signup")
    public String signup(Model model){
        System.out.println("Services loading");
        UserForm form=new UserForm();
        model.addAttribute("formData", form);
        return "signup";
    }
    
    @PostMapping("/do-register")
    public String register(@Valid @ModelAttribute("formData") UserForm formData, BindingResult bindingResult, HttpSession session) {
    	System.out.println("processing registration");
    	//fetch user 
    	//System.out.println("User is: "+formData.toString());
    	
    	//validate user
    	if(bindingResult.hasErrors()) {
    		System.out.println("Enter in errors");
    		return "signup";
    	}
    	
    	User user=new User();
    	user.setName(formData.getName());
    	user.setEmail(formData.getEmail());
    	user.setPassword(formData.getPassword());
    	user.setAbout(formData.getAbout());
    	user.setPhoneNumber(formData.getPhoneNumber());
    	System.out.println("User is: "+user.toString());
    	service.save(user);
    	
    	session.setAttribute("message", "Successfully Registered");
    	
    	return "redirect:/signup";
    }
    

}
