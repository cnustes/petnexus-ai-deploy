package com.petnexusai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for creating or updating a pet.
 */
@Data
public class PetRequestDto {
    @NotBlank(message = "Pet name cannot be blank.")
    @Size(min = 1, max = 50)
    private String name;

    @NotBlank(message = "Species name cannot be blank.")
    private String speciesName; // We receive the name as a String

    @NotBlank(message = "Breed name cannot be blank.")
    private String breedName; // We receive the name as a String

    @PastOrPresent(message = "Birth date cannot be in the future.")
    private LocalDate birthDate;
}