package com.chatop_back.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.chatop_back.api.model.Users;
import com.chatop_back.api.payload.AuthSuccess;
import com.chatop_back.api.payload.request.LoginRequest;
import com.chatop_back.api.payload.request.RegisterRequest;
import com.chatop_back.api.repository.UserRepository;
import com.chatop_back.api.service.CustomUserDetailsService;
import com.chatop_back.api.utils.JwtUtil;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<AuthSuccess> login(@RequestBody LoginRequest loginRequest) {
        // Authentification via AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthSuccess(token));
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthSuccess> register(@RequestBody RegisterRequest registerRequest) {
        // Vérifier si l'email est déjà utilisé
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        // Création de l'utilisateur avec un mot de passe crypté
        Users newUser = Users.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(newUser);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthSuccess(token));
    }
    
    // L'endpoint pour récupérer les informations de l'utilisateur connecté
    @GetMapping("/me")
    public ResponseEntity<Users> me(Authentication authentication) {
        String email = authentication.getName();
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        return ResponseEntity.ok(user);
    }
}
