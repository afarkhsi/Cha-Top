package com.chatop_back.api.payload.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RentalRequest {
    private String name;
    private Double surface;
    private Double price;
    private String description;
    // Si la photo est envoyée en tant que fichier, déclare-la ainsi :
    private MultipartFile picture;
    // Si tu prévois d'envoyer une URL en string, utilise String:
    // private String picture;
}
