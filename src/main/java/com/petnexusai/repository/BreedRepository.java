package com.petnexusai.repository;

import com.petnexusai.model.Breed;
import com.petnexusai.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BreedRepository extends JpaRepository<Breed, UUID> {
    List<Breed> findBySpeciesId(UUID speciesId);
    Optional<Breed> findByNameAndSpecies(String name, Species species); // <-- ADD THIS METHOD
}