package com.chatop_back.api.payload;

import java.util.List;

import com.chatop_back.api.model.Rental;

public class RentalsResponse {
    private List<Rental> rentals;

    public RentalsResponse(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
}