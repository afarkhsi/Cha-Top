package com.chatop_back.api.payload;

import java.time.LocalDateTime;

public record RentalSingleResponse(
	    Integer id,
	    String name,
	    Double surface,
	    Double price,
	    String picture,
	    String description,
	    Integer owner_id,
	    LocalDateTime created_at,
	    LocalDateTime updated_at
	) {
	}
