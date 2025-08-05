package com.petnexusai.repository;

import com.petnexusai.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, UUID> {
    Optional<Species> findByName(String name); // <-- ADD THIS METHOD
}