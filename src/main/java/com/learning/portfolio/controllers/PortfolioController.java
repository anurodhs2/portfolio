package com.learning.portfolio.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learning.portfolio.dto.ApiResponse;
import com.learning.portfolio.dto.UserPortfolioResponse;
import com.learning.portfolio.entities.UserPortfolioEntity;
import com.learning.portfolio.services.UserPortfolioService;
import com.learning.portfolio.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api")
public class PortfolioController {
	Logger logger = LoggerFactory.getLogger(PortfolioController.class);

	@Autowired
	JwtUtil jwtutil;
	
	@Autowired
	private UserPortfolioService userPortfolioService;
	 
    @GetMapping("/portfolio")
    public ResponseEntity<ApiResponse> getUserPortfolio(HttpServletRequest request) {
		try {
			logger.info("user portfolio fetch start");
			String authHeader = request.getHeader("Authorization");
			String username = jwtutil.extractUsername(authHeader.substring(7));
			UserPortfolioEntity portfolio = userPortfolioService.getUserPortfolioByUsername(username);
			ApiResponse response;
			UserPortfolioResponse userPortfolioResponse;
			if(portfolio != null) {
				userPortfolioResponse = new UserPortfolioResponse(username, portfolio.getInvestment(), portfolio.getAssets(), portfolio.getDebts());
				response = new ApiResponse("Portfolio not found for user: "+ username, false, userPortfolioResponse);
				return ResponseEntity.ok(response);
			} else {				
				response = new ApiResponse("Portfolio not found for user: "+ username, false);
		        return ResponseEntity.status(404).body(response);
			}
		} catch (Exception e){
			e.printStackTrace();
			ApiResponse response = new ApiResponse("Invalid or missing authorization header", false);
	        return ResponseEntity.status(401).body(response);
		}
    }
    
    @PutMapping("/portfolio")
    public ResponseEntity<ApiResponse> updateUserPortfolio(@RequestBody UserPortfolioEntity updatedPortfolio, HttpServletRequest request) {
		try {
			logger.info("user portfolio updation start");
			String authHeader = request.getHeader("Authorization");
			String username = jwtutil.extractUsername(authHeader.substring(7));
			ApiResponse response;
			
			// Ensure the updated portfolio belongs to the authenticated user
            if (!username.equals(updatedPortfolio.getUsername())) {
    			response = new ApiResponse("You can only update your own portfolio", false);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
 
            UserPortfolioEntity existingPortfolio = userPortfolioService.getUserPortfolioByUsername(username);
            if (existingPortfolio != null) {
                // Update the portfolio
                existingPortfolio.setInvestment(updatedPortfolio.getInvestment());
                existingPortfolio.setAssets(updatedPortfolio.getAssets());
                existingPortfolio.setDebts(updatedPortfolio.getDebts());
 
                UserPortfolioEntity savedPortfolio = userPortfolioService.updateUserPortfolio(existingPortfolio);

    			response = new ApiResponse("Portfolio updated successfully", true, savedPortfolio);
                return ResponseEntity.ok(response);
            } else {
            	UserPortfolioEntity newPortfolio = new UserPortfolioEntity();
            	newPortfolio.setUsername(username);
            	newPortfolio.setInvestment(updatedPortfolio.getInvestment());
            	newPortfolio.setAssets(updatedPortfolio.getAssets());
            	newPortfolio.setDebts(updatedPortfolio.getDebts());
            	
                UserPortfolioEntity savedPortfolio = userPortfolioService.updateUserPortfolio(newPortfolio);

    			response = new ApiResponse("Portfolio updated successfully", true, savedPortfolio);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
			
		} catch (Exception e){
			e.printStackTrace();
			ApiResponse response = new ApiResponse("Invalid or missing authorization header", false);
	        return ResponseEntity.status(401).body(response);
		}
    }
}
