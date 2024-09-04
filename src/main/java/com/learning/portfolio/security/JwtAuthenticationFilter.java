package com.learning.portfolio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.learning.portfolio.entities.UserEntity;
import com.learning.portfolio.services.UserService;
import com.learning.portfolio.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   @Autowired
   private JwtUtil jwtUtil;

   @Autowired
   private UserService userService;
	
   public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
	   this.jwtUtil = jwtUtil;
	   this.userService = userService;
   }
   
   @Override
   protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException, ServletException {
	   final String authHeader = request.getHeader("Authorization");
	   
	   if(authHeader == null || !authHeader.startsWith("Bearer ")) {
		   filterChain.doFilter(request, response);
		   return;
	   }
	   
	   try {
		   final String jwt = authHeader.substring(7);
		   final String username = jwtUtil.extractUsername(jwt);
		   
		   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		   
		   if(username != null && authentication == null) {
			   UserEntity userEntity = userService.findByUsername(username);
			   
			   if(jwtUtil.validateToken(jwt, userEntity.getUsername())) {
				   UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEntity, null, null);
				   authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				   SecurityContextHolder.getContext().setAuthentication(authToken);
			   }
		   }
		   filterChain.doFilter(request, response);
	   } catch (ExpiredJwtException e) {
		   throw new ExpiredJwtException(null, null, e.getMessage());
	   } catch (SignatureException e) {
		   throw new SignatureException(e.getMessage());
	   } catch (Exception e) {
		   e.printStackTrace();
		   try {
			   throw new Exception(e.getMessage());
		   } catch (Exception re) {
			   throw new RuntimeException(re);
		   }
	   }
   }
}