package com.emp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO for employee name and salary information
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EmpDTO {

    private String name;   // Employee's name
    private double salary; // Employee's salary

}
