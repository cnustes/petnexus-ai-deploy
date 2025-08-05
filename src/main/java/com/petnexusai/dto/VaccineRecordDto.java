package com.petnexusai.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for transferring vaccine record data.
 * Includes validation constraints.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineRecordDto {
    private UUID id;

    @NotBlank(message = "Vaccine name cannot be blank.")
    private String vaccineName;

    @NotNull(message = "Date administered cannot be null.")
    @PastOrPresent(message = "Date administered cannot be in the future.")
    private LocalDate dateAdministered;

    @Future(message = "Next due date must be in the future.")
    private LocalDate nextDueDate;
}