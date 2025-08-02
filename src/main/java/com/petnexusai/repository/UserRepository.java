package com.petnexusai.repository;

import com.petnexusai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link User} entities.
 * Provides standard CRUD operations and custom query methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * This method is crucial for the login process.
     *
     * @param email The email address to search for.
     * @return An {@link Optional} containing the found user, or empty if not found.
     */
    Optional<User> findByEmail(String email);
}