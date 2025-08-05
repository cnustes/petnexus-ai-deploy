package com.petnexusai.service.species;

import com.petnexusai.dto.BreedDto;
import com.petnexusai.dto.SpeciesDto;
import com.petnexusai.model.Breed;
import com.petnexusai.model.Species;
import com.petnexusai.repository.BreedRepository;
import com.petnexusai.repository.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpeciesService {

    private final SpeciesRepository speciesRepository;
    private final BreedRepository breedRepository;

    public List<SpeciesDto> getAllSpecies() {
        return speciesRepository.findAll().stream()
                .map(this::convertToSpeciesDto)
                .collect(Collectors.toList());
    }

    public List<BreedDto> getBreedsBySpeciesId(UUID speciesId) {
        return breedRepository.findBySpeciesId(speciesId).stream()
                .map(this::convertToBreedDto)
                .collect(Collectors.toList());
    }

    private SpeciesDto convertToSpeciesDto(Species species) {
        return new SpeciesDto(species.getId(), species.getName());
    }

    private BreedDto convertToBreedDto(Breed breed) {
        return new BreedDto(breed.getId(), breed.getName());
    }
}