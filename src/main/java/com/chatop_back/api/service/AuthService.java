package com.chatop_back.api.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop_back.api.model.Users;
import com.chatop_back.api.payload.AuthSuccess;
import com.chatop_back.api.payload.UserDto;
import com.chatop_back.api.payload.request.LoginRequest;
import com.chatop_back.api.payload.request.RegisterRequest;
import com.chatop_back.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public AuthSuccess login(LoginRequest loginRequest) {
        Users user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        // Utiliser correctement votre JWTService qui attend un objet Users
        String token = jwtService.generateToken(user);
        return AuthSuccess.builder().token(token).build();
    }
    
    public AuthSuccess register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        
        Users user = Users.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        user = userRepository.save(user);
        
        // Utiliser correctement votre JWTService qui attend un objet Users
        String token = jwtService.generateToken(user);
        return AuthSuccess.builder().token(token).build();
    }
    
    public UserDto getCurrentUser(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}