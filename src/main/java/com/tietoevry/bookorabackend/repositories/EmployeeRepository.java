package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmailIgnoreCase(String email);
}
