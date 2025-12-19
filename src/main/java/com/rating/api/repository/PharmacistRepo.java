package com.rating.api.repository;

import com.rating.api.domain.Pharmacist;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepo extends JpaRepository<Pharmacist, UUID> {
  Optional<Pharmacist> getPharmacistsByEmail(String email);
  Optional<Pharmacist> getPharmacistsByName(String name);
}
