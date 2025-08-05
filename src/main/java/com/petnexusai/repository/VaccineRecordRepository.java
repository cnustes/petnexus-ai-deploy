package com.petnexusai.repository;

import com.petnexusai.model.VaccineRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for VaccineRecord entity.
 * Provides CRUD operations for vaccine records.
 */
@Repository
public interface VaccineRecordRepository extends JpaRepository<VaccineRecord, UUID> {
    // Basic CRUD methods are inherited from JpaRepository
}