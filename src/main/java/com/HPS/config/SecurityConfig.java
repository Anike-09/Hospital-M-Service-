package com.HPS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    private final JWTAuthenticationFilter jwtAuthFilter;
//
//    public SecurityConfig(JWTAuthenticationFilter jwtAuthFilter) {
//        this.jwtAuthFilter = jwtAuthFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    	http
//        .cors().disable()
//        .csrf().disable()
//        .authorizeRequests()
//        .requestMatchers("/api/patients/**").permitAll()
//        .anyRequest().authenticated()
//        .and()
//        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
////            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
	
	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	 private final JWTAuthenticationFilter jwtAuthenticationFilter;

	    public SecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter) {
	        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    }

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf().disable()
	            .authorizeRequests()
	                .requestMatchers("/public/**").permitAll()
	                .requestMatchers("/api/**").authenticated()
	                .anyRequest().authenticated()
	            .and()
	            .exceptionHandling().authenticationEntryPoint( customAuthenticationEntryPoint) // Your custom entry point for unauthorized access
	            .and()
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session management
	            .and()
	            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

	        return http.build();
	    }
	
}



