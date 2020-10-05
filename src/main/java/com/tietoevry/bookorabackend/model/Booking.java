package com.tietoevry.bookorabackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"employee", "zone"})
@EqualsAndHashCode(exclude = {"employee", "zone"})
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Zone zone;
}
