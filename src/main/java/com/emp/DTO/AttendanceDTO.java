package com.emp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO for employee attendance information
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AttendanceDTO {

    private String name;        // Employee's name
    private int attendance;     // Employee's attendance count
}
