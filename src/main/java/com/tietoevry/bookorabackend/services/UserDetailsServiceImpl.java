package com.tietoevry.bookorabackend.services;


import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee user = employeeRepository.findByEmailIgnoreCase(email);
		System.out.println(user.getEmail());

        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + email);
        }


        return UserDetailsImpl.build(user);
    }

}
