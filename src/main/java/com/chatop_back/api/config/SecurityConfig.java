package com.chatop_back.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configuration de la sécurité pour les routes
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                // Autoriser l'accès sans authentification à Swagger et aux routes d'API
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/api/rentals/**", "/api/messages/**",
                        "/api/rentals/**",
                        "/api/user/**",
                        "/api/auth/**") // Ajoute l'accès aux API
                .permitAll() // Permet l'accès sans authentification
                .anyRequest().authenticated() // Toute autre requête doit être authentifiée
            )
            .formLogin((formLogin) -> formLogin
                .loginPage("/login") // URL de la page de connexion (si tu en as une)
                .permitAll() // Permet l'accès à la page de login sans authentification
            );

        return http.build(); // Important dans Spring Boot 3.x et Spring Security 6.x
    }
}

