package com.petnexusai.repository;

import com.petnexusai.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Pet entity.
 * Provides CRUD operations for pets.
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {

    // Custom query method to find all pets belonging to a specific user by their email
    List<Pet> findByUserEmail(String email);
}