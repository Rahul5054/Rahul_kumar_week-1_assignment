package com.emp.controller;

import com.emp.DTO.AttendanceDTO;
import com.emp.DTO.EmpDTO;
import com.emp.model.Emp;
import com.emp.service.EmpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("employees") // Base URL for employee-related APIs
public class EmpController {

    @Autowired
    private EmpService employeeService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')") // Only ADMIN role can create a new employee
    public ResponseEntity<Emp> saveEmployeeDetails(@Valid @RequestBody Emp employee) {
        Emp savedEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED); // Return created employee with 201 status
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") // Both ADMIN and USER can retrieve all employees
    public ResponseEntity<List<Emp>> retrieveAllEmployeesPageable() {
        List<Emp> employees = employeeService.retrieveAllEmployees();
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 if no employees found
        } else {
            return new ResponseEntity<>(employees, HttpStatus.OK); // Return 200 with employee list
        }
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") // Both ADMIN and USER can view a specific employee by ID
    public ResponseEntity<Emp> retrieveEmployeeById(@PathVariable Integer id) {
        Emp employee = employeeService.retrieveEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK); // Return employee details with 200 status
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')") // Only ADMIN role can delete an employee
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>("Employee deleted successfully !!", HttpStatus.OK); // Return success message
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')") // Only ADMIN can update an employee's details
    public ResponseEntity<Emp> UpdateEmployee(@PathVariable Integer id, @Valid @RequestBody Emp emp) {
        return ResponseEntity.ok(employeeService.UpdateEmployee(id, emp)); // Return updated employee with 200 status
    }

    @GetMapping("/salaries")
    @PreAuthorize("hasAuthority('ADMIN')") // Only ADMIN can view salaries of all employees
    public ResponseEntity<List<EmpDTO>> viewSalaries() {
        List<EmpDTO> salaries = employeeService.viewSalary();
        return ResponseEntity.ok(salaries); // Return list of employee salaries with 200 status
    }

    @GetMapping("/attendance")
    @PreAuthorize("hasAuthority('ADMIN')") // Only ADMIN can view attendance of all employees
    public ResponseEntity<List<AttendanceDTO>> viewAttendance() {
        List<AttendanceDTO> attendanceList = employeeService.viewAttendance();
        return ResponseEntity.ok(attendanceList); // Return list of employee attendance with 200 status
    }

    @GetMapping("/department-employee-count")
    @PreAuthorize("hasAuthority('ADMIN')") // Only ADMIN can view employee count by department
    public ResponseEntity<List<Map<String, Object>>> getDepartmentWiseEmployeeCount() {
        List<Map<String, Object>> employeeCountByDepartment = employeeService.getDepartmentWiseEmployeeCount();
        return ResponseEntity.ok(employeeCountByDepartment); // Return department-wise employee count with 200 status
    }
}
