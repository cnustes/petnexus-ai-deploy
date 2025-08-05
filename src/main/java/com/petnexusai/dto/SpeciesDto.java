package com.petnexusai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for transferring Species data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDto {
    private UUID id;
    private String name;
}