package com.abc.cmspring2.repositories;

import com.abc.cmspring2.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Allows searching for employees by name (case-insensitive)
    List<Employee> findByNameContainingIgnoreCase(String keyword);
}