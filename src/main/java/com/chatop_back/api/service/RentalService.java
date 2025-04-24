package com.chatop_back.api.service;

import com.chatop_back.api.model.Rental;
import com.chatop_back.api.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental createRental(Rental rental) {
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());
        return rentalRepository.save(rental);
    }

    // Méthode pour mettre à jour une rental par son ID
    public Rental updateRental(Long id, Rental rental) {
        // Vérifie si la rental existe déjà
        Rental existingRental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        // Mettre à jour les informations de la rental
        existingRental.setName(rental.getName()); 
        existingRental.setDescription(rental.getDescription());
        existingRental.setPrice(rental.getPrice());
   
        // Ajoute d'autres champs à mettre à jour selon tes besoins.

        // Sauvegarde la rental mise à jour dans la base de données
        return rentalRepository.save(existingRental);
    }

    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }
}
