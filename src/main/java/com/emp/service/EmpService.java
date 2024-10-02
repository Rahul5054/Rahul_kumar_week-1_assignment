package com.emp.service;

import com.emp.DTO.AttendanceDTO;
import com.emp.DTO.EmpDTO;
import com.emp.model.Emp;

import java.util.List;
import java.util.Map;

public interface EmpService {

    Emp createEmployee(Emp employee);

    Emp retrieveEmployeeById(Integer id);

    void deleteEmployeeById(Integer id);

    List<Emp> retrieveAllEmployees();

    Emp UpdateEmployee(Integer id, Emp emp);

    List<EmpDTO> viewSalary();

    List<AttendanceDTO> viewAttendance();

    List<Map<String, Object>> getDepartmentWiseEmployeeCount();


}
