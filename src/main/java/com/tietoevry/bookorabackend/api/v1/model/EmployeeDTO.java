package com.tietoevry.bookorabackend.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private long id ;
 //   @NotNull(message = "You must fill in first name.")
    private String firstName;
 //   @NotNull(message = "You must fill in last name.")
    private String lastName;
 //   @NotNull(message = "You must fill in email.")
   // @Email
    private String email;
  //  @NotNull(message = "You must fill in first password.")
    private String password;
    private Set<String> role;

    @JsonProperty("employee_url")
    private String employeeUrl;



}
