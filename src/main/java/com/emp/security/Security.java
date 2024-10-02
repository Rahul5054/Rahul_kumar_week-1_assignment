package com.emp.security;

import com.emp.impl.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // Enables method-level security annotations like @PreAuthorize
public class Security {

    @Autowired
    private CustomUserDetails customUserDetailService;

    // Defines the security filter chain for handling HTTP security
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection
                .authorizeHttpRequests(req -> req
                        .requestMatchers(
                                "/v3/api-docs"  // Allows access to Swagger API docs without authentication
                        )
                        .permitAll()
                        .anyRequest().authenticated()  // Any other request must be authenticated
                )
                .httpBasic(Customizer.withDefaults())  // Enables HTTP basic authentication
                .build();
    }

    // Defines the authentication provider to use custom user details and password encoder
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());  // Sets password encoder
        provider.setUserDetailsService(customUserDetailService);  // Uses custom user details service for authentication
        return provider;
    }

    // Defines the password encoder (BCrypt)
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
