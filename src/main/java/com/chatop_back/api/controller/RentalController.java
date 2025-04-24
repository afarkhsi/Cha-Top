package com.chatop_back.api.controller;

import com.chatop_back.api.model.Rental;
import com.chatop_back.api.payload.RentalSingleResponse;
import com.chatop_back.api.payload.RentalsResponse;
import com.chatop_back.api.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;
    
    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Get all rentals", description = "Retrieve a list of all rentals")
    @GetMapping("")
    public RentalsResponse getAllRentals() {
        List<Rental> rentals = rentalService.getRentals();
        List<RentalSingleResponse> rentalSingleResponses = rentals.stream()
                .map(rental -> modelMapper.map(rental, RentalSingleResponse.class))
                .collect(Collectors.toList());
        return new RentalsResponse(rentalSingleResponses);
    }
   
    @Operation(summary = "Get rental by ID", description = "Retrieve rental details by rental ID")
    @GetMapping("/{id}")
    public Optional<Rental> getRentalById(@Parameter(description = "ID of the rental to retrieve") @PathVariable Long id) {
        return rentalService.getRentalById(id);
    }

    @Operation(summary = "Create a new rental", description = "Create a new rental entry")
    @PostMapping
    public Rental createRental(@RequestBody Rental rental) {
        return rentalService.createRental(rental);
    }

    @Operation(summary = "Update a rental", description = "Update an existing rental entry")
    @PutMapping("/{id}")
    public Rental updateRental(@PathVariable Long id, @RequestBody Rental rental) {
        return rentalService.updateRental(id, rental);
    }

    @Operation(summary = "Delete a rental", description = "Delete a rental by its ID")
    @DeleteMapping("/{id}")
    public void deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
    }
}
