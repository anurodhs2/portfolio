package com.learning.portfolio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.learning.portfolio.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

   @Autowired
   private JwtUtil jwtUtil;

   @Override
   public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
           throws AuthenticationException {
       String username = request.getParameter("username");
       String password = request.getParameter("password");

       if (username != null && password != null) {
           UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
           return this.getAuthenticationManager().authenticate(authToken);
       }
       return null;
   }

   @Override
   protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                           FilterChain chain, Authentication authResult) throws IOException, ServletException {
       String username = ((UserDetails) authResult.getPrincipal()).getUsername();
       String token = jwtUtil.generateToken(username);

       // Add token to the response body
       response.setContentType("application/json");
       response.getWriter().write("{\"message\": \"Login successful\", \"success\": true, \"token\": \"" + token + "\"}");
   }

   @Override
   protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                             AuthenticationException failed) throws IOException, ServletException {
       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
       response.setContentType("application/json");
       response.getWriter().write("{\"message\": \"Invalid username or password\", \"success\": false}");
   }
}