package com.tietoevry.bookorabackend.bootstrap;

import com.tietoevry.bookorabackend.model.ERole;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.Role;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner{

    private final EmployeeRepository employeeRepository;

    private  final RoleRepository roleRepository;

    public DataLoader(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
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

        Role role1 = new Role();
        role1.setName(ERole.ROLE_USER);
        roleRepository.save(role1);

        Role role2 = new Role();
        role2.setName(ERole.ROLE_MODERATOR);
        roleRepository.save(role2);


        Role role3 = new Role();
        role3.setName(ERole.ROLE_ADMIN);
        roleRepository.save(role3);






    }
}
