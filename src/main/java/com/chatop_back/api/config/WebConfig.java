package com.chatop_back.api.config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


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
    

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Obtenir le chemin absolu du dossier uploads
        String absoluteUploadPath = Paths.get("uploads").toAbsolutePath().toString();
        // Exemple : "file:/C:/chemin/vers/votre/projet/uploads/"
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + absoluteUploadPath + "/");
    }
}