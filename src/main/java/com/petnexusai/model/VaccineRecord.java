package com.petnexusai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a single vaccination record for a pet.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vaccine_records")
public class VaccineRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String vaccineName;

    @Column(nullable = false)
    private LocalDate dateAdministered;

    private LocalDate nextDueDate;

    // A vaccine record belongs to one pet.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}