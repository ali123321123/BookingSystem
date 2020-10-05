package com.tietoevry.bookorabackend.bootstrap;

import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner{

    private final EmployeeRepository employeeRepository;

    public DataLoader(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Employee employee1 = new Employee();
        employee1.setEmail("1@abc.com");
        employee1.setPassword("123123");
        employee1.setFirstName("John");
        employee1.setLastName("Johnson");
        employeeRepository.save(employee1);
    }
}
