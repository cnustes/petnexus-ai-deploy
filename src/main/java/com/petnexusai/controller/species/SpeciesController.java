package com.petnexusai.controller.species;

import com.petnexusai.dto.BreedDto;
import com.petnexusai.dto.SpeciesDto;
import com.petnexusai.service.species.SpeciesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/species")
@RequiredArgsConstructor
public class SpeciesController {

    private final SpeciesService speciesService;

    @Operation(summary = "Get all available species")
    @GetMapping
    public ResponseEntity<List<SpeciesDto>> getAllSpecies() {
        return ResponseEntity.ok(speciesService.getAllSpecies());
    }

    @Operation(summary = "Get all breeds for a specific species")
    @GetMapping("/{speciesId}/breeds")
    public ResponseEntity<List<BreedDto>> getBreedsBySpecies(@PathVariable UUID speciesId) {
        return ResponseEntity.ok(speciesService.getBreedsBySpeciesId(speciesId));
    }
}