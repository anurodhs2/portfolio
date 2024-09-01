package com.learning.portfolio.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.learning.portfolio.services.UserService;

@Controller
public class LoginController {
	Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
    private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
    public String userLogin(@RequestParam String username, @RequestParam String password, Model model) {
		System.out.print(username);
		System.out.print(password);
		logger.info("user login attempt");
        if (userService.authenticate(username, password)) {
            // Login successful, redirect to dashboard or any other page
            model.addAttribute("user", userService.findByUsername(username));
            return "redirect:/dashboard";
        } else {
            // Authentication failed
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}
