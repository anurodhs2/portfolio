package com.learning.portfolio.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.portfolio.dto.ApiResponse;
import com.learning.portfolio.entities.UserEntity;
import com.learning.portfolio.models.User;
import com.learning.portfolio.services.UserService;


@RestController
@RequestMapping("/api")
public class RegistrationController {
	Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	@Autowired
    private UserService userService;
	 
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
		logger.info("user registeration attempt");
		try {
			UserEntity userEntity = new UserEntity(user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword());
			boolean isRegistered = userService.registerUser(userEntity);
			ApiResponse response;
			if(isRegistered) {	
				response = new ApiResponse("User registered successfully", true);
			} else {
				response = new ApiResponse("User already exist", false);	
			}
	        return ResponseEntity.ok(response);
			
		} catch (Exception e){
			e.printStackTrace();
			ApiResponse response = new ApiResponse("User registration failed", false);
	        return ResponseEntity.ok(response);
		}
    }
}
