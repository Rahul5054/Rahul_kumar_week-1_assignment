package com.emp.repo;

import com.emp.model.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmpRepo extends JpaRepository<Emp, Integer> {

    // Finds an employee by their email (used for authentication)
    Optional<Emp> findByEmail(String email);

    // Custom query to get the count of employees grouped by department
    @Query("SELECT e.department, COUNT(e) FROM Emp e GROUP BY e.department")
    List<Object[]> findDepartmentWiseEmployeeCount();
}
