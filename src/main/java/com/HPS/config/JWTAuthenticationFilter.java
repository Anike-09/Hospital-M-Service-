package com.HPS.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Override  
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  
          throws ServletException, IOException {  
      
       String jwtToken = request.getHeader("Authorization");  
       logger.info("JWT Token received: {}", jwtToken);  
      
       if (jwtToken != null && jwtToken.startsWith("Bearer ")) {  
          jwtToken = jwtToken.substring(7);  
          if (JwtUtil.validateToken(jwtToken)) {  
            logger.info("JWT Token is valid");  
      
            Claims claims = Jwts.parserBuilder()  
                  .setSigningKey(JwtUtil.KEY) 
                  .build()  
                  .parseClaimsJws(jwtToken)  
                  .getBody();  
      
            String username = claims.getSubject();  
            List<String> roles = claims.get("authorities", List.class);  
      
            
            
            if (roles != null) {  
               List<GrantedAuthority> authorities = roles.stream()  
                    .map(SimpleGrantedAuthority::new)  
                    .collect(Collectors.toList());  
      
               
               logger.info("Username  -- " + username);
               logger.info("authorities  -- " + authorities);
               
               UserDetails userDetails = new User(username, "", authorities);  
      
               UsernamePasswordAuthenticationToken authenticationToken =  
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);  
      
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);  
               logger.info("Authentication set in security context");  
            } else {  
               logger.error("No roles found in token");  
            }  
          } else {  
            logger.error("Invalid JWT Token");  
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");  
            return;  
          }  
       } else {  
          logger.warn("No JWT Token found in the request header or invalid token format");  
       }  
      
       filterChain.doFilter(request, response);  
    }
    }