package com.learning.portfolio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.learning.portfolio.entities.UserEntity;
import com.learning.portfolio.models.User;
import com.learning.portfolio.services.UserService;


@Controller
public class RegistrationController {
	@Autowired
    private UserService userService;
	
	@GetMapping("/register")
	public String register(Model model) {
        model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String userRegister(@ModelAttribute User user, Model model) {
		UserEntity userEntity = new UserEntity(user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword());
		userService.save(userEntity);
		return "redirect:/login?registrationSuccess";
	}
}
