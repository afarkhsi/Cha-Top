package com.chatop_back.api.controller;
import com.chatop_back.api.model.Rental;
import com.chatop_back.api.payload.RentalSingleResponse;
import com.chatop_back.api.payload.RentalsResponse;
import com.chatop_back.api.payload.request.RentalUpdateRequest;
import com.chatop_back.api.service.FileStorageService;
import com.chatop_back.api.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;
 
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;
    
    @Value("${upload.directory:uploads}")
    private String uploadDir;

    public RentalController(RentalService rentalService, ModelMapper modelMapper, FileStorageService fileStorageService) {
        this.rentalService = rentalService;
        this.modelMapper = modelMapper;
        this.fileStorageService = fileStorageService;
    }

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
    public ResponseEntity<RentalSingleResponse> getRentalById(
            @Parameter(description = "ID of the rental to retrieve") @PathVariable Long id) {
        Rental rental = rentalService.getRentalById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        RentalSingleResponse response = modelMapper.map(rental, RentalSingleResponse.class);
        return ResponseEntity.ok(response);
    }

    // Endpoint pour créer une location avec upload d'image
    @Operation(summary = "Create a new rental", description = "Create a new rental entry with image upload")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RentalSingleResponse> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile picture) {
        
        try {  
            // Sauvegarde de l'image
            String fileName = fileStorageService.storeFile(picture);
            
            // Obtention de l'email de l'user authentifié
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            if (email == null || email.equals("anonymousUser")) {
                return ResponseEntity.status(401).build();
            }
            
            // Créer l'objet Rental (sans set owner)
            Rental rental = new Rental();
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setDescription(description);
            rental.setPicture(fileName);
            
            // La gestion de l'owner et des dates se fait dans le service
            Rental createdRental = rentalService.createRental(rental, email);
            RentalSingleResponse response = modelMapper.map(createdRental, RentalSingleResponse.class);
            
            return ResponseEntity.ok(response);
            
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new RentalSingleResponse(null, "Error: " + e.getMessage(), null, null, null, null, null, null, null));
        }
    }

    @Operation(summary = "Update a rental", description = "Update an existing rental entry")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updateRental(
            @PathVariable Long id, @ModelAttribute RentalUpdateRequest rentalRequest) { 

        rentalService.updateRental(id, rentalRequest);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental updated !");
        
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Delete a rental", description = "Delete a rental by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }
}
