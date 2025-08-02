package com.petnexusai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Represents a user in the application.
 * This class is a JPA entity mapped to the "users" table and also serves
 * as the user details object for Spring Security.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * Returns the authorities granted to the user.
     * For now, all users are granted the 'ROLE_USER'.
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Returns the username used to authenticate the user. In our case, it's the email.
     * @return The user's email.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired.
     * @return true, as we don't have account expiration logic.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * @return true, as we don't have account locking logic.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     * @return true, as we don't have credential expiration logic.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * @return true, as all users are enabled by default.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}