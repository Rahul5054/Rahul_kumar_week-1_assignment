package com.emp.model;

import com.emp.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity  // Marks the class as a JPA entity
@AllArgsConstructor  // Generates a constructor with all fields
@Getter  // Lombok annotation to generate getter methods
@Setter  // Lombok annotation to generate setter methods
@NoArgsConstructor  // Generates a no-args constructor
@ToString  // Generates a toString method
public class Emp implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates primary key values
    private int id;

    @NotNull(message = "Name is Mandatory")
    private String name;  // Employee name

    @NotNull
    @Email(message = "Email should be valid")  // Email validation
    @NotBlank(message = "Email is mandatory")  // Ensures email field is not blank
    @Column(unique = true)  // Ensures email is unique
    private String email;  // Employee email

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{6,}$",
            message = "Password must be at least 6 characters long and contain at least one letter and one number")
    private String password;  // Employee password with validation

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth = LocalDate.now();  // Employee date of birth with a default value

    @NotBlank(message = "Department is mandatory")
    private String department;  // Employee department

    @Min(value = 0, message = "Salary must be greater than or equal to 0")
    private double salary;  // Employee salary

    @NotBlank(message = "City is mandatory")
    private String city;  // Employee city

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)  // Specifies the roles are stored as strings
    @CollectionTable(name = "employee_roles", joinColumns = @JoinColumn(name = "employee_id"))  // Maps roles to the employee
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();  // Set of roles assigned to the employee

    private int attendance;  // Employee attendance

    // Implementation of UserDetails methods for Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converts roles to GrantedAuthority objects for Spring Security
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.password;  // Returns the employee's password
    }

    @Override
    public String getUsername() {
        return this.email;  // Returns the employee's username (email in this case)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Specifies that the account is not expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Specifies that the account is not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Specifies that the credentials are not expired
    }

    @Override
    public boolean isEnabled() {
        return true;  // Specifies that the account is enabled
    }
}
