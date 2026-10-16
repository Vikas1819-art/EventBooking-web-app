package com.example.eventBooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
//        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
        	    .requestMatchers(
        	        "/login",
        	        "/register",
        	        "/WEB-INF/views/**",
        	        "/css/**",
        	        "/js/**"
        	        
        	    ).permitAll()
        	    
        	    
        	    
        	    .requestMatchers(
        	    		"/create-event",
        	    		"/create-venue",
        	    		"/users-page",
        	    		"/create-user",
        	    		"/update-user",
                        "/generate-page",
                        "/update-venue"
        	    		).hasRole("ADMIN")
        	    
        	    
        	    
        	    .requestMatchers(
        	    		HttpMethod.GET , "/api/**").hasRole("ADMIN")
        	    
        	    .requestMatchers(HttpMethod.POST,"/api/bookings/bulk").authenticated()
        	    .requestMatchers(HttpMethod.PUT ,"/api/bookings/**").authenticated()
        	    
        	    
        	    .requestMatchers(
        	    		HttpMethod.POST , "/api/**").hasRole("ADMIN")
        	    
        	    .requestMatchers(
        	    		HttpMethod.PUT , "/api/**").hasRole("ADMIN")
        	    
        	    .requestMatchers(
        	    		HttpMethod.DELETE , "/api/**").hasRole("ADMIN")
        	    
        	    
        	    
        	    .anyRequest().authenticated()
        	)
        
        
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/events-page", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            
            
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}