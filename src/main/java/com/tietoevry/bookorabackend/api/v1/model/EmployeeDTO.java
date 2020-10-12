package com.tietoevry.bookorabackend.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private long id ;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> role;

    @JsonProperty("employee_url")
    private String employeeUrl;



}
