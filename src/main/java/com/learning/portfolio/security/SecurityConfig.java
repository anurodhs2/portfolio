package com.learning.portfolio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired(required=true)
	private UserDetailsService userDetailsService;
	
    @Bean
    AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setUserDetailsService(userDetailsService);
    	provider.setPasswordEncoder(new BCryptPasswordEncoder());
    	return provider;
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
            .csrf(csrf->{
            	try {
                	csrf.disable().authorizeHttpRequests(requests-> requests
    	        			.requestMatchers("/api/register").permitAll()
    	                    .requestMatchers("/api/login").permitAll()
    	                    .anyRequest()
    	                    .authenticated()
    	    			).sessionManagement(session -> session
    	    				.invalidSessionUrl("/api/login?invalidSessionUrl")
    	    				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    	    				.maximumSessions(1)
    	    				.maxSessionsPreventsLogin(true)
    	    				.expiredUrl("/logout")
    					)
                		.authenticationProvider(authenticationProvider())
                		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            });
        return httpSecurity.build();
    }
}