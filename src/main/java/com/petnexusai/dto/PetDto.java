package com.petnexusai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DTO for transferring pet data, including associated records.
 * Includes validation constraints for incoming data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {
    private UUID id;

    @NotBlank(message = "Pet name cannot be blank.")
    @Size(min = 1, max = 50, message = "Pet name must be between 1 and 50 characters.")
    private String name;

    @NotBlank(message = "Species cannot be blank.")
    private String species;

    private String breed;

    @PastOrPresent(message = "Birth date cannot be in the future.")
    private LocalDate birthDate;

    // These are typically used for responses, not requests, so no validation needed here.
    private List<VaccineRecordDto> vaccineRecords;
    private List<AppointmentDto> appointments;
}