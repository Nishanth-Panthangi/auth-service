package com.example.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    private static final Logger logger = LogManager.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String SECRET_KEY; // Secret key for signing JWT tokens, should be stored securely

    public String extractUsername(String token) {
        logger.info("Extracting username from token");
        // Extract the username (subject) from the token
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        logger.info("Extracting claim from token");
        // Extract specific claims from the token using a claims resolver function
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        logger.info("Extracting all claims from token");
        // Parse the token to extract all claims
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String generateToken(String username) {
        logger.info("Generating token for username: " + username);
        // Generate a JWT token for the given username
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        logger.info("Creating token for subject: " + subject);
        // Create the JWT token with claims, subject, issue date, expiration date, and signature
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // Set the subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issued time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Set expiration time (10 hours)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Sign the token with the HS256 algorithm and secret key
                .compact();
    }
}