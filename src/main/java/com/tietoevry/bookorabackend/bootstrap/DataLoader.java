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

        LoadEmployees();
    }

    private void LoadEmployees() {
        Employee employee1 = new Employee("Per", "Peterson", "per.peterson@tietoevry.com","111");
        Employee employee2 = new Employee("John", "Johnson", "oslomet7@gmail.com","222");
        Employee employee3 = new Employee("Kari", "Hansen", "oslomet6@gmail.com","333");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

    }
}
