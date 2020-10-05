package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
