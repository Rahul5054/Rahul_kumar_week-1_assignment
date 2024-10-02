package com.emp.impl;

import com.emp.model.Emp;
import com.emp.repo.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service  // Marks this class as a Spring service component
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private EmpRepo employeeRepository;  // Injects the employee repository to access database operations

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieves the employee by email (username) from the repository
        Emp employee = employeeRepository.findByEmail(username).orElse(null);

        // Throws a runtime exception if the employee is not found
        if (employee == null) {
            throw new RuntimeException("Employee not found !!");
        }

        // Returns the employee as a UserDetails object, or throws UsernameNotFoundException if not found
        return employeeRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with email : " + username));
    }
}
