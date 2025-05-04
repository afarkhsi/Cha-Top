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
            rental != null ? rental.getId() : null,
            rental != null ? rental.getName() : null,
            rental != null ? rental.getSurface() : null,
            rental != null ? rental.getPrice() : null,
            rental != null ? rental.getPicture() : null,
            rental != null ? rental.getDescription() : null,
            rental != null && rental.getOwner() != null ? rental.getOwner().getId() : null,
            rental != null ? rental.getCreated_at() : null,
            rental != null ? rental.getUpdated_at() : null
        );
    }
}