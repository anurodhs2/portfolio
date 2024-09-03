package com.learning.portfolio.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learning.portfolio.dto.ApiResponse;
import com.learning.portfolio.models.User;


@RestController
@RequestMapping("/api")
public class PortfolioController {
	Logger logger = LoggerFactory.getLogger(PortfolioController.class);
	 
    @GetMapping("/portfolio")
    public ResponseEntity<ApiResponse> getUserPortfolio() {
		logger.info("user registeration attempt");
		try {
			// TO DO user portfolio
			ApiResponse response = new ApiResponse("User portfolio fetched", true);
	        return ResponseEntity.ok(response);
			
		} catch (Exception e){
			e.printStackTrace();
			ApiResponse response = new ApiResponse("User portfolio fetching failed", false);
	        return ResponseEntity.ok(response);
		}
    }
    
    @PutMapping("/portfolio")
    public ResponseEntity<ApiResponse> updateUserPortfolio() {
		logger.info("user registeration attempt");
		try {
			// TO DO user portfolio update
			ApiResponse response = new ApiResponse("User portfolio updated", true);
	        return ResponseEntity.ok(response);
			
		} catch (Exception e){
			e.printStackTrace();
			ApiResponse response = new ApiResponse("User portfolio updating failed", false);
	        return ResponseEntity.ok(response);
		}
    }
}
