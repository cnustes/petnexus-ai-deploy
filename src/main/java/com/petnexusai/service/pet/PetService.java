package com.petnexusai.service.pet;

import com.petnexusai.dto.AppointmentDto;
import com.petnexusai.dto.PetRequestDto;
import com.petnexusai.dto.PetResponseDto;
import com.petnexusai.dto.VaccineRecordDto;
import com.petnexusai.model.*;
import com.petnexusai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for handling business logic related to pets, vaccines, and appointments.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final VaccineRecordRepository vaccineRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final SpeciesRepository speciesRepository;
    private final BreedRepository breedRepository;

    /**
     * Creates a new pet for a given user.
     * It looks up Species and Breed entities based on the names provided in the DTO.
     *
     * @param petRequestDto The DTO containing the new pet's data.
     * @param userEmail The email of the user who will own the pet.
     * @return The DTO of the newly created pet.
     */
    public PetResponseDto createPet(PetRequestDto petRequestDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userEmail));

        Species species = speciesRepository.findByName(petRequestDto.getSpeciesName())
                .orElseThrow(() -> new RuntimeException("Species not found: " + petRequestDto.getSpeciesName()));

        Breed breed = breedRepository.findByNameAndSpecies(petRequestDto.getBreedName(), species)
                .orElseThrow(() -> new RuntimeException("Breed '" + petRequestDto.getBreedName() + "' not found for species '" + petRequestDto.getSpeciesName() + "'"));

        Pet pet = new Pet();
        pet.setName(petRequestDto.getName());
        pet.setBirthDate(petRequestDto.getBirthDate());
        pet.setUser(user);
        pet.setSpecies(species);
        pet.setBreed(breed);

        Pet savedPet = petRepository.save(pet);
        return convertToPetResponseDto(savedPet);
    }

    /**
     * Retrieves all pets belonging to a specific user.
     *
     * @param userEmail The email of the user.
     * @return A list of PetResponseDto objects.
     */
    @Transactional(readOnly = true)
    public List<PetResponseDto> getPetsByUser(String userEmail) {
        return petRepository.findByUserEmail(userEmail).stream()
                .map(this::convertToPetResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new vaccine record to a specific pet, verifying ownership.
     *
     * @param petId The ID of the pet.
     * @param vaccineDto The DTO with the vaccine record data.
     * @param userEmail The email of the authenticated user.
     * @return The DTO of the newly created vaccine record.
     */
    public VaccineRecordDto addVaccineRecord(UUID petId, VaccineRecordDto vaccineDto, String userEmail) {
        Pet pet = findPetAndVerifyOwnership(petId, userEmail);

        VaccineRecord record = new VaccineRecord();
        record.setVaccineName(vaccineDto.getVaccineName());
        record.setDateAdministered(vaccineDto.getDateAdministered());
        record.setNextDueDate(vaccineDto.getNextDueDate());
        record.setPet(pet);

        VaccineRecord savedRecord = vaccineRecordRepository.save(record);
        return convertToVaccineRecordDto(savedRecord);
    }

    /**
     * Adds a new appointment to a specific pet, verifying ownership.
     *
     * @param petId The ID of the pet.
     * @param appointmentDto The DTO with the appointment data.
     * @param userEmail The email of the authenticated user.
     * @return The DTO of the newly created appointment.
     */
    public AppointmentDto addAppointment(UUID petId, AppointmentDto appointmentDto, String userEmail) {
        Pet pet = findPetAndVerifyOwnership(petId, userEmail);

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        appointment.setReason(appointmentDto.getReason());
        appointment.setVetClinicName(appointmentDto.getVetClinicName());
        appointment.setNotes(appointmentDto.getNotes());
        appointment.setPet(pet);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToAppointmentDto(savedAppointment);
    }

    // --- Private Helper and Mapper Methods ---

    private Pet findPetAndVerifyOwnership(UUID petId, String userEmail) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + petId));
        if (!pet.getUser().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("User does not have permission to access this pet");
        }
        return pet;
    }

    private PetResponseDto convertToPetResponseDto(Pet pet) {
        return PetResponseDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .speciesName(pet.getSpecies() != null ? pet.getSpecies().getName() : null)
                .breedName(pet.getBreed() != null ? pet.getBreed().getName() : null)
                .birthDate(pet.getBirthDate())
                .vaccineRecords(pet.getVaccineRecords().stream().map(this::convertToVaccineRecordDto).collect(Collectors.toList()))
                .appointments(pet.getAppointments().stream().map(this::convertToAppointmentDto).collect(Collectors.toList()))
                .build();
    }

    private VaccineRecordDto convertToVaccineRecordDto(VaccineRecord record) {
        return new VaccineRecordDto(
                record.getId(),
                record.getVaccineName(),
                record.getDateAdministered(),
                record.getNextDueDate()
        );
    }

    private AppointmentDto convertToAppointmentDto(Appointment appointment) {
        return new AppointmentDto(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getReason(),
                appointment.getVetClinicName(),
                appointment.getNotes()
        );
    }
}