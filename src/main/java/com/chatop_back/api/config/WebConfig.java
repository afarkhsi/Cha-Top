package com.chatop_back.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Autoriser CORS sur toutes les routes du backend
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Autoriser l'origine du frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes HTTP autorisées
                .allowedHeaders("*"); // Autoriser tous les en-têtes
    }   
}