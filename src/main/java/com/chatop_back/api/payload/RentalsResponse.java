package com.chatop_back.api.payload;

public record RentalsResponse(Iterable<RentalSingleResponse> rentals) {}