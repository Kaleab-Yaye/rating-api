package com.rating.api.repository;


import com.rating.api.domain.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PharmacistRepo extends JpaRepository<Pharmacist, UUID>{
    Optional<Pharmacist> getPharmacistsByEmail(String email);
}
