package com.chatop_back.api.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;


import com.chatop_back.api.payload.AuthSuccess;
import com.chatop_back.api.payload.UserDto;
import com.chatop_back.api.payload.request.LoginRequest;
import com.chatop_back.api.payload.request.RegisterRequest;
import com.chatop_back.api.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<AuthSuccess> login(@RequestBody LoginRequest loginRequest) {
        // Authentification via AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        AuthSuccess authSuccess = authService.login(loginRequest);
        return ResponseEntity.ok(authSuccess);
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<AuthSuccess> register(@RequestBody RegisterRequest registerRequest) {
        AuthSuccess authSuccess = authService.register(registerRequest);
        return ResponseEntity.ok(authSuccess);
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication authentication) {
        String email = authentication.getName();
        UserDto userDto = authService.getCurrentUserByEmail(email); 
        return ResponseEntity.ok(userDto);
    }
}
