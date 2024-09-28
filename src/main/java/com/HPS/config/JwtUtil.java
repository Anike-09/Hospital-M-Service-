package com.HPS.config;



import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.time.DateUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;



public class JwtUtil {
	
	private JwtUtil() {}
	
	private static final String ISSUER = "coding Streams auth Server" ;
	
	
//	private static final Logger logger = Logger.getLogger(ForgotService.class);
	
	
//	private static final SecretKey secretKey = Jwts.SIG.HS256.key().build() ;
	
//	  private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	private static final String SECRET_KEY_STRING = "YourSecretKeyHereMakeItLongAndSecureAtLeast256BitsLong";
    private static final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    
	
	public static boolean valideToken(String jwtToken) {
		
		return parseToken(jwtToken).isPresent();
	}

	private static Optional<Claims> parseToken(String jwtToken) {
		// TODO Auto-generated method stub
		var jwtPaerser = Jwts.parser()
		.verifyWith(secretKey)
		.build();
		
		try {
		
		 return Optional.of(jwtPaerser.
		   parseSignedClaims(jwtToken)
		   .getPayload());

		}catch (JwtException  | IllegalArgumentException e) {
			// TODO: handle exception
//			logger.error("JWt exception Occured" ) ;    
			System.out.println("JWt exception Occured" + e.getMessage());
		}
		
		return Optional.empty() ;
	}
	

	public static Optional<String> getUsernameFromToken(String jwtToken) {
	
		var claimsOptional = parseToken(jwtToken);
		
		if(claimsOptional.isPresent()) {
			return Optional.of(claimsOptional.get().getSubject());
		}
		return Optional.empty() ;
	}
}