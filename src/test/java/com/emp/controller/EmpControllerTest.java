package com.emp.controller;

import com.emp.DTO.AttendanceDTO;
import com.emp.DTO.EmpDTO;
import com.emp.model.Emp;
import com.emp.service.EmpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmpControllerTest {

    @InjectMocks
    private EmpController empController;

    @Mock
    private EmpService empService;

    private Emp employee;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Emp();
        employee.setId(1);
        employee.setName("John Doe");
        employee.setEmail("john.doe@example.com");
        employee.setPassword("Password123");
        employee.setSalary(50000);
        employee.setCity("New York");
    }

    @Test
    public void testSaveEmployeeDetails() {
        when(empService.createEmployee(any(Emp.class))).thenReturn(employee);

        ResponseEntity<Emp> response = empController.saveEmployeeDetails(employee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(empService, times(1)).createEmployee(any(Emp.class));
    }

    @Test
    public void testRetrieveAllEmployeesPageable() {
        List<Emp> employees = Arrays.asList(employee);
        when(empService.retrieveAllEmployees()).thenReturn(employees);

        ResponseEntity<List<Emp>> response = empController.retrieveAllEmployeesPageable();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
        verify(empService, times(1)).retrieveAllEmployees();
    }

    @Test
    public void testRetrieveEmployeeById() {
        when(empService.retrieveEmployeeById(1)).thenReturn(employee);

        ResponseEntity<Emp> response = empController.retrieveEmployeeById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(empService, times(1)).retrieveEmployeeById(1);
    }

    @Test
    public void testDeleteEmployeeById() {
        doNothing().when(empService).deleteEmployeeById(1);

        ResponseEntity<String> response = empController.deleteEmployeeById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee deleted successfully !!", response.getBody());
        verify(empService, times(1)).deleteEmployeeById(1);
    }

    @Test
    public void testUpdateEmployee() {
        when(empService.UpdateEmployee(anyInt(), any(Emp.class))).thenReturn(employee);

        ResponseEntity<Emp> response = empController.UpdateEmployee(1, employee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(empService, times(1)).UpdateEmployee(anyInt(), any(Emp.class));
    }
    @Test
    public void testViewSalaries() {
        EmpDTO empDTO = new EmpDTO("John Doe", 50000);
        List<EmpDTO> salaries = Arrays.asList(empDTO);
        when(empService.viewSalary()).thenReturn(salaries);

        ResponseEntity<List<EmpDTO>> response = empController.viewSalaries();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(salaries, response.getBody());
        verify(empService, times(1)).viewSalary();
    }

    @Test
    public void testViewAttendance() {
        AttendanceDTO attendanceDTO = new AttendanceDTO("John Doe", 10);
        List<AttendanceDTO> attendanceList = Arrays.asList(attendanceDTO);
        when(empService.viewAttendance()).thenReturn(attendanceList);

        ResponseEntity<List<AttendanceDTO>> response = empController.viewAttendance();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(attendanceList, response.getBody());
        verify(empService, times(1)).viewAttendance();
    }

}
