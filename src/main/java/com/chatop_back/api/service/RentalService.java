package com.chatop_back.api.service;

import com.chatop_back.api.model.Rental;
import com.chatop_back.api.model.Users;
import com.chatop_back.api.payload.request.RentalUpdateRequest;
import com.chatop_back.api.repository.RentalRepository;
import com.chatop_back.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    // Nouvelle version de createRental qui gère l'assignation de l'owner et les dates
    public Rental createRental(Rental rental, String userEmail) {
        Users owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));
        rental.setOwner(owner);
        rental.setCreated_at(LocalDateTime.now());
        rental.setUpdated_at(LocalDateTime.now());
        return rentalRepository.save(rental);
    }

  
    public void updateRental(Long id, RentalUpdateRequest rentalRequest) {
        Rental existingRental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        // Mettre à jour uniquement les champs envoyés par le client
        existingRental.setName(rentalRequest.getName());
        existingRental.setSurface(rentalRequest.getSurface());
        existingRental.setPrice(rentalRequest.getPrice());
        existingRental.setDescription(rentalRequest.getDescription());
        existingRental.setUpdated_at(LocalDateTime.now());

        rentalRepository.save(existingRental);
    }

    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }
}
