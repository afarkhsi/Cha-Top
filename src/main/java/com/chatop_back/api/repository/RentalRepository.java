package com.chatop_back.api.repository;

import com.chatop_back.api.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    // Tu peux ajouter des méthodes personnalisées ici si besoin, par exemple :
    // List<Rental> findByOwnerId(Long ownerId);
}
