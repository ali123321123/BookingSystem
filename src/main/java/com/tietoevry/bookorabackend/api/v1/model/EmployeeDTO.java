package com.tietoevry.bookorabackend.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private long id ;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @JsonProperty("employee_url")
    private String employeeUrl;



}
