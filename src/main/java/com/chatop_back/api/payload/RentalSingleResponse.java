package com.chatop_back.api.payload;

import java.time.LocalDateTime;

import com.chatop_back.api.model.Rental;

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
	 public RentalSingleResponse(Rental rental) {
	        this(
	            rental.getId(),
	            rental.getName(),
	            rental.getSurface(),
	            rental.getPrice(),
	            rental.getPicture(),
	            rental.getDescription(),
	            rental.getOwner().getId(), // ⬅️ ici on expose juste l'ID
	            rental.getCreatedAt(),
	            rental.getUpdatedAt()
	        );
	   }
}



