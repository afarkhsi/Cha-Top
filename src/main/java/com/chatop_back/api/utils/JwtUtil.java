package com.chatop_back.api.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    // Une chaîne Base64 encodée plus longue comme secret (doit être d'au moins 512 bits après décodage)
    private final String jwtSecretBase64 = "c29tZXJhbmRvbXN0cmluZ3RvZ2V0aGF0ZXZlcnl0aGluZ3dpbGwtYmUtYXQ1MTJi"
            + "aXRzLWxlbmd0aC1vZi01MTI="; // Exemple, veille à utiliser un secret généré de manière sécurisée.
    private final long jwtExpirationMs = 86400000; // 24h

    // Génère un SecretKey à partir de la clé Base64
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretBase64);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Génère le token à partir du UserDetails
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // Récupère le username (email) à partir du token
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    // Validation du token
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = getUsernameFromToken(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            // Le token n'est pas valide
        }
        return false;
    }

 // Vérifie l'expiration du token
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()  // Utilise parserBuilder() au lieu de parser()
                .setSigningKey(getSigningKey()) // Utilise la clé de signature
                .build()
                .parseClaimsJws(token)          // Parse le token JWT
                .getBody()
                .getExpiration();               // Récupère la date d'expiration
        
        return expiration.before(new Date());    // Vérifie si le token est expiré
    }
}
