package com.petnexusai.controller.pet;

import com.petnexusai.dto.AppointmentDto;
import com.petnexusai.dto.PetRequestDto;
import com.petnexusai.dto.PetResponseDto;
import com.petnexusai.dto.VaccineRecordDto;
import com.petnexusai.service.pet.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for handling pet-related API requests.
 * All endpoints in this controller are protected and require authentication.
 */
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PetController {

    private final PetService petService;

    @Operation(summary = "Create a new pet for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<PetResponseDto> createPet(@Valid @RequestBody PetRequestDto petDto, Authentication authentication) {
        String userEmail = authentication.getName();
        PetResponseDto createdPet = petService.createPet(petDto, userEmail);
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all pets for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved pets"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<PetResponseDto>> getMyPets(Authentication authentication) {
        String userEmail = authentication.getName();
        List<PetResponseDto> pets = petService.getPetsByUser(userEmail);
        return ResponseEntity.ok(pets);
    }

    @Operation(summary = "Add a vaccine record to a specific pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vaccine record added successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found"),
            @ApiResponse(responseCode = "403", description = "User does not own this pet")
    })
    @PostMapping("/{petId}/vaccines")
    public ResponseEntity<VaccineRecordDto> addVaccine(
            @PathVariable UUID petId,
            @Valid @RequestBody VaccineRecordDto vaccineDto,
            Authentication authentication) {

        String userEmail = authentication.getName();
        VaccineRecordDto createdRecord = petService.addVaccineRecord(petId, vaccineDto, userEmail);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    @Operation(summary = "Add an appointment to a specific pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment added successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found"),
            @ApiResponse(responseCode = "403", description = "User does not own this pet")
    })
    @PostMapping("/{petId}/appointments")
    public ResponseEntity<AppointmentDto> addAppointment(
            @PathVariable UUID petId,
            @Valid @RequestBody AppointmentDto appointmentDto,
            Authentication authentication) {

        String userEmail = authentication.getName();
        AppointmentDto createdAppointment = petService.addAppointment(petId, appointmentDto, userEmail);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }
}