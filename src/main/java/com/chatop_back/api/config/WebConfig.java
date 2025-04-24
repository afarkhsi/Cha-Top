package com.chatop_back.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permettre à toutes les requêtes de localhost:4200 (ou l'URL de votre frontend)
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200") // Remplacez par l'URL de votre frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}