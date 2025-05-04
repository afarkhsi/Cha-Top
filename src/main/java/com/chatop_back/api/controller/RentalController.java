package com.chatop_back.api.controller;

import com.chatop_back.api.model.Rental;
import com.chatop_back.api.payload.RentalSingleResponse;
import com.chatop_back.api.payload.RentalsResponse;
import com.chatop_back.api.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;
    
    @Autowired
    private ModelMapper modelMapper;

    // Endpoint pour récupérer toutes les locations
    @Operation(summary = "Get all rentals", description = "Retrieve a list of all rentals")
    @GetMapping("")
    public RentalsResponse getAllRentals() {
        List<Rental> rentals = rentalService.getRentals();
        // Conversion de chaque entité en DTO
        List<RentalSingleResponse> rentalSingleResponses = rentals.stream()
                .map(rental -> modelMapper.map(rental, RentalSingleResponse.class))
                .collect(Collectors.toList());
        return new RentalsResponse(rentalSingleResponses);
    }
   
    // Endpoint pour récupérer une location par son ID (retourne le DTO)
    @Operation(summary = "Get rental by ID", description = "Retrieve rental details by rental ID")
    @GetMapping("/{id}")
    public ResponseEntity<RentalSingleResponse> getRentalById(@Parameter(description = "ID of the rental to retrieve") 
                                                              @PathVariable Long id) {
        Rental rental = rentalService.getRentalById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        RentalSingleResponse response = modelMapper.map(rental, RentalSingleResponse.class);
        return ResponseEntity.ok(response);
    }

    // Endpoint pour créer une location (retourne le DTO de la location créée)
    @Operation(summary = "Create a new rental", description = "Create a new rental entry")
    @PostMapping
    public ResponseEntity<RentalSingleResponse> createRental(@RequestBody Rental rental) {
        Rental createdRental = rentalService.createRental(rental);
        RentalSingleResponse response = modelMapper.map(createdRental, RentalSingleResponse.class);
        return ResponseEntity.ok(response);
    }

    // Endpoint pour mettre à jour une location (retourne le DTO de la location mise à jour)
    @Operation(summary = "Update a rental", description = "Update an existing rental entry")
    @PutMapping("/{id}")
    public ResponseEntity<RentalSingleResponse> updateRental(@PathVariable Long id, @RequestBody Rental rental) {
        Rental updatedRental = rentalService.updateRental(id, rental);
        RentalSingleResponse response = modelMapper.map(updatedRental, RentalSingleResponse.class);
        return ResponseEntity.ok(response);
    }

    // Endpoint pour supprimer une location
    @Operation(summary = "Delete a rental", description = "Delete a rental by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }
}
