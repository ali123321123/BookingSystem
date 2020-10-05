package com.tietoevry.bookorabackend.api.v1.model;

import java.util.Date;

import com.tietoevry.bookorabackend.model.Zone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long id;
    private Date date;
    private Long employeeId;
    private Long zoneId;

}
