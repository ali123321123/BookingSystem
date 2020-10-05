package com.tietoevry.bookorabackend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Employee employee;
}
