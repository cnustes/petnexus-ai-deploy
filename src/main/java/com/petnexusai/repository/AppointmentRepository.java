package com.petnexusai.repository;

import com.petnexusai.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for Appointment entity.
 * Provides CRUD operations for appointments.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    // Basic CRUD methods are inherited from JpaRepository
}