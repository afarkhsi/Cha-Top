package com.chatop_back.api.payload;

import com.chatop_back.api.model.Rental;
import java.time.LocalDateTime;

public record RentalSingleResponse(
    Long id,
    String name,
    Double surface,
    Double price,
    String picture,
    String description,
    Long owner_id,
    LocalDateTime created_at,
    LocalDateTime updated_at
) {
    // Constructeur pour créer la réponse à partir d'un objet Rental
    public RentalSingleResponse(Rental rental) {
        this(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                // Ici, vous concaténez le chemin pour former l'URL complète
                "http://localhost:3001/uploads/" + rental.getPicture(),
                rental.getDescription(),
                rental.getOwner().getId(),
                rental.getCreated_at(),
                rental.getUpdated_at()
        );
    }
}