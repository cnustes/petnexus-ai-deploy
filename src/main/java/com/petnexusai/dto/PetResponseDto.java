package com.petnexusai.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DTO for sending pet data to the client.
 */
@Data
@Builder
public class PetResponseDto {
    private UUID id;
    private String name;
    private String speciesName;
    private String breedName;
    private LocalDate birthDate;
    private List<VaccineRecordDto> vaccineRecords;
    private List<AppointmentDto> appointments;
}