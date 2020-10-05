package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
