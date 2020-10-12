package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.ERole;
import com.tietoevry.bookorabackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
