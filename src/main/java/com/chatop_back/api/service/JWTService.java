package com.chatop_back.api.service;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.chatop_back.api.model.Users;
import com.chatop_back.api.utils.JwtUtil;
import org.springframework.security.core.userdetails.User;

@Service
public class JWTService {

    private final JwtUtil jwtUtil;

    public JWTService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Génère un JWT pour l'utilisateur donné.
     */
    public String generateToken(Users user) {
        // Adaptation du modèle Users à UserDetails
        User userDetails = new User(user.getEmail(), "", Collections.emptyList());
        return jwtUtil.generateToken(userDetails);
    }

    /**
     * Récupère l'email (username) à partir du token.
     */
    public String getEmailFromToken(String token) {
        return jwtUtil.getUsernameFromToken(token);
    }

    /**
     * Vérifie si le token est valide pour un utilisateur donné.
     */
    public boolean validateToken(String token, Users user) {
        User userDetails = new User(user.getEmail(), "", Collections.emptyList());
        return jwtUtil.validateToken(token, userDetails);
    }
}

