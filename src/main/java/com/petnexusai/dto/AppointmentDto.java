package com.petnexusai.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for transferring appointment data.
 * Includes validation constraints.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
    private UUID id;

    @NotNull(message = "Appointment date cannot be null.")
    @Future(message = "Appointment date must be in the future.")
    private LocalDateTime appointmentDate;

    @NotBlank(message = "Reason for appointment cannot be blank.")
    private String reason;

    private String vetClinicName;
    private String notes;
}