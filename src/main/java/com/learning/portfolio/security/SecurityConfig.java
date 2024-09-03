package com.learning.portfolio.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
            .csrf(csrf->{
            	try {
                	csrf.disable().authorizeHttpRequests(requests-> requests
    	        			.requestMatchers("/api/register").permitAll()
    	                    .requestMatchers("/api/login").permitAll()
    	                    .requestMatchers("/api/portfolio").permitAll()
    	                    .anyRequest()
    	                    .authenticated()
    	    			).sessionManagement(session -> session
    	    				.invalidSessionUrl("/api/login?invalidSessionUrl")
    	    				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    	    				.maximumSessions(1)
    	    				.maxSessionsPreventsLogin(true)
    	    				.expiredUrl("/logout")
    					);
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            });
        return httpSecurity.build();
    }
}