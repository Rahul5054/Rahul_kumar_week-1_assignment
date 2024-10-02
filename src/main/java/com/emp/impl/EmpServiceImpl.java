package com.emp.impl;

import com.emp.DTO.AttendanceDTO;
import com.emp.DTO.EmpDTO;
import com.emp.model.Emp;
import com.emp.repo.EmpRepo;
import com.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service  // Marks the class as a service component managed by Spring
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpRepo empRepo;  // Injects the employee repository for database operations

    @Autowired
    private PasswordEncoder passwordEncoder;  // Injects the password encoder for securing employee passwords

    @Override
    public Emp createEmployee(Emp employee) {
        // Encode the password before saving the employee
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return empRepo.save(employee);  // Save the employee to the database
    }

    @Override
    public Emp retrieveEmployeeById(Integer id) {
        // Retrieve the employee by ID, or return null if not found
        return empRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteEmployeeById(Integer id) {
        // Delete the employee by their ID
        empRepo.deleteById(id);
    }

    @Override
    public List<Emp> retrieveAllEmployees() {
        // Fetch all employees from the database
        return empRepo.findAll();
    }

    public Emp UpdateEmployee(Integer id, Emp updatedemp) {
        // Find the existing employee by ID
        Emp emp = empRepo.findById(id).orElse(null);

        // Update the employee's details with new information
        emp.setName(updatedemp.getName());
        emp.setEmail(updatedemp.getEmail());
        emp.setPassword(updatedemp.getPassword());
        emp.setCity(updatedemp.getCity());
        emp.setDepartment(updatedemp.getDepartment());
        emp.setSalary(updatedemp.getSalary());
        emp.setRoles(updatedemp.getRoles());
        emp.setDateOfBirth(updatedemp.getDateOfBirth());

        return empRepo.save(emp);  // Save the updated employee
    }

    @Override
    public List<EmpDTO> viewSalary() {
        // Fetch all employees from the database
        List<Emp> employees = empRepo.findAll();

        // Map each employee to an EmpDTO containing name and salary
        List<EmpDTO> empDTOs = employees.stream().map(emp -> {
            EmpDTO empDTO = new EmpDTO();
            empDTO.setName(emp.getName());
            empDTO.setSalary(emp.getSalary());
            return empDTO;
        }).collect(Collectors.toList());

        return empDTOs;
    }

    @Override
    public List<AttendanceDTO> viewAttendance() {
        // Fetch all employees from the repository
        List<Emp> employees = empRepo.findAll();

        // Map each employee to an AttendanceDTO containing name and attendance
        List<AttendanceDTO> attDTOs = employees.stream().map(emp -> {
            AttendanceDTO attDTO = new AttendanceDTO();
            attDTO.setName(emp.getName());
            attDTO.setAttendance(emp.getAttendance());
            return attDTO;
        }).collect(Collectors.toList());

        return attDTOs;
    }

    @Override
    public List<Map<String, Object>> getDepartmentWiseEmployeeCount() {
        // Fetch department-wise employee count as an Object array from the repository
        List<Object[]> results = empRepo.findDepartmentWiseEmployeeCount();
        List<Map<String, Object>> response = new ArrayList<>();

        // Map the results to a List of Maps with department name and employee count
        for (Object[] result : results) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("Department Name", result[0]);  // Department name
            entry.put("Employee Count", result[1]);  // Employee count
            response.add(entry);
        }

        return response;
    }
}
