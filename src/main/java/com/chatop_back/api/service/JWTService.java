package com.chatop_back.api.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chatop_back.api.model.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
    
    // Clé secrète pour signer les tokens (à mettre dans application.properties en production)
    @Value("${jwt.secret:mysecretkey}")
    private String jwtSecret;
    
    // Durée de validité du token en millisecondes (24h par défaut)
    @Value("${jwt.expiration:86400000}")
    private long jwtExpiration;

    // Générer un token JWT pour un utilisateur
    public String generateToken(Users user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Utilisation de HS512 pour une clé plus longue
                .compact();
    }
    
    // Extraire l'ID utilisateur du token
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Utilisation de la clé générée
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        return Long.parseLong(claims.getSubject());
    }
    
    // Valider le token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Générer une clé secrète sécurisée
    private Key getSigningKey() {
        // Si la clé secrète est définie dans les propriétés, on l'utilise
        // Sinon, on génère une clé sécurisée de 512 bits
        if (jwtSecret != null && !jwtSecret.isEmpty()) {
            byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
            return Keys.hmacShaKeyFor(keyBytes);  // Utilisation de la clé configurée
        } else {
            // Si aucune clé n'est définie dans les propriétés, on génère une clé sécurisée
            return Keys.secretKeyFor(SignatureAlgorithm.HS256);  // HMAC-SHA512, plus sécurisé
        }
    }
}
