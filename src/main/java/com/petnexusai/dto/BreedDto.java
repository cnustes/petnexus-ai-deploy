package com.petnexusai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for transferring Breed data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BreedDto {
    private UUID id;
    private String name;
}