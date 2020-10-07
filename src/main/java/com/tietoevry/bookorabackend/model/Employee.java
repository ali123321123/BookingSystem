package com.tietoevry.bookorabackend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"admin", "confirmationToken"})
@EqualsAndHashCode(exclude = {"admin", "confirmationToken"})
@Entity
public class Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "You must fill in first name.")
    private String firstName;

    @NotNull(message = "You must fill in last name.")
    private String lastName;

    @NotNull(message = "You must fill in e-mail.")
    @Email
    private String email;

    @NotNull
    private String password;

    @OneToMany(mappedBy = "employee")
    private Set<Booking> employeeBookings = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee")
    private Admin admin;

    private boolean isEnabled;

    @OneToOne(mappedBy = "employee")
    private ConfirmationToken confirmationToken;

    public Employee(@NotNull(message = "You must fill in first name.") String firstName, @NotNull(message = "You must fill in last name.") String lastName, @NotNull(message = "You must fill in e-mail.") @Email String email, @NotNull String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
