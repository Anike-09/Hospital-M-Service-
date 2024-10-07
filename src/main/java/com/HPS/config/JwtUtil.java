package com.HPS.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    
    
    private static final String SECRET_KEY = "YourSecretKeyHereMakeItLongAndSecureAtLeast256BitsLong";
    public static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));



    public static Authentication getAuthenticationFromToken(String jwtToken) {  
    	   try {  
    	      Claims claims = Jwts.parserBuilder()  
    	        .setSigningKey(KEY)  
    	        .build()  
    	        .parseClaimsJws(jwtToken)  
    	        .getBody();  
    	  
    	      String username = claims.getSubject();  
    	      List<String> roles = claims.get("authorities", List.class);  
    	  
    	      if (roles != null) {  
    	        List<GrantedAuthority> authorities = roles.stream()  
    	           .map(SimpleGrantedAuthority::new)  
    	           .collect(Collectors.toList());  
    	  
    	        UserDetails userDetails = new User(username, "", authorities);  
    	  
    	        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);  
    	      } else {  
    	        logger.error("No roles found in token");  
    	        return null;  
    	      }  
    	   } catch (JwtException e) {  
    	      logger.error("Error extracting authentication from token: {}", e.getMessage());  
    	      return null;  
    	   }  
    	}
    
    // Validate the token
    public static boolean validateToken(String jwtToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token has expired: {}", e.getMessage());
            return false;
        } catch (JwtException e) {
            logger.error("Invalid JWT Token: {}", e.getMessage());
            return false;
        }
    }
}