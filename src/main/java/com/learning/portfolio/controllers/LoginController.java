package com.learning.portfolio.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learning.portfolio.dto.ApiResponse;
import com.learning.portfolio.dto.UserResponse;
import com.learning.portfolio.entities.UserEntity;
import com.learning.portfolio.models.UserDetail;
import com.learning.portfolio.services.UserService;

@RestController
@RequestMapping("/api")
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
    private UserService userService;
	
	@PostMapping("/login")
    public ResponseEntity<ApiResponse> userLogin(@RequestBody UserDetail user) {
		String username = user.getUsername();
		String password = user.getPassword();
		logger.info("user login attempt");
        if (userService.authenticate(username, password)) {
        	UserEntity userDetail = userService.findByUsername(username);
        	UserResponse userResponse = new UserResponse(userDetail.getFirstname(), userDetail.getLastname(), userDetail.getUsername());
			ApiResponse response = new ApiResponse("User logged in successfully", true, userResponse);
            return ResponseEntity.ok(response);
        } else {
			ApiResponse response = new ApiResponse("User login failed", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
