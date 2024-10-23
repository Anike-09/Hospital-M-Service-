package com.HPS.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration  
@EnableWebSecurity  
public class SecurityConfig {  
  
   @Autowired  
   private JWTAuthenticationFilter jwtValidationFilter;  
  
   @Autowired  
   private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;  
  
   @Bean  
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {  
      http  
         .csrf().disable()  
         .authorizeRequests()  
         .requestMatchers("/public/**","/swagger-ui/**", "/v3/api-docs/**").permitAll()  
         .requestMatchers("/api/**").hasAuthority("ADMIN")   
         .anyRequest().authenticated()  
         .and()  
         .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)  
         .and()  
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  
         .and()  
         .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)  
         .cors(cors -> cors.configurationSource(corsConfig()));  
     
      return http.build();  
   }
  
   @Bean  
   public CorsConfigurationSource corsConfig() {  
      CorsConfiguration corsConfiguration = new CorsConfiguration();  
      corsConfiguration.setAllowedOrigins(Arrays.asList("*"));  
      corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));  
      corsConfiguration.setAllowedHeaders(Arrays.asList("*"));  
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
      source.registerCorsConfiguration("/**", corsConfiguration);  
      return source;  
   }  
   }