package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.EmployeeDTO;
import com.tietoevry.bookorabackend.api.v1.model.EmployeeListDTO;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RoleRepository;
import com.tietoevry.bookorabackend.security.jwt.JwtResponse;
import com.tietoevry.bookorabackend.security.jwt.JwtUtils;
import com.tietoevry.bookorabackend.security.jwt.MessageResponse;
import com.tietoevry.bookorabackend.services.ConfirmationTokenService;
import com.tietoevry.bookorabackend.services.EmployeeService;
import com.tietoevry.bookorabackend.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000"})
@Tag(name = "Employee", description = "Employee API")
@RestController
@RequestMapping(EmployeeController.BASE_URL)
public class EmployeeController {
    public static final String BASE_URL = "/api/v1/employees";
    private final EmployeeService employeeService;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService employeeService, ConfirmationTokenService confirmationTokenService) {
        this.employeeService = employeeService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EmployeeListDTO getEmployeeList() {
        return employeeService.getAllEmployees();
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody EmployeeDTO loginRequest) {

        //Authenticate and return an Authentication object that can be used to find user information
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        //Update SecurityContext using Authentication object
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Generate JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        //Get UserDetails from Authentication object
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); //authentication.getPrincipal() return object of org.springframework.security.core.userdetails.User

        //Get roles from UserDetails
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        //Response that contains JWT, id, email and roles
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                roles));
    }


    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNewEmployee(@RequestBody EmployeeDTO employeeDTO) {

        employeeService.createNewEmployee(employeeDTO);
        return ResponseEntity.ok(new MessageResponse("Success!"));
    }


    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO){
        return employeeService.saveEmployeeByDTO(id, employeeDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployeeDTO(id);
    }

    @GetMapping("/alle")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?>  test1(){
        return ResponseEntity.ok(new MessageResponse("Hei på deg"));
    }

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>  test2(){
        return ResponseEntity.ok(new MessageResponse("Hei admin"));
    }




}
