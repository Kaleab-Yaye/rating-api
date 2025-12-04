package com.rating.api.repository;

import com.rating.api.entity.Pharmacist;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, UUID> {
  Optional<Pharmacist> findByEmail(String email);

  Optional<Pharmacist> findByName(String name);

  @Query("SELECT p FROM Pharmacist p WHERE p.pharmacy.id = :pharmacyId")
  List<Pharmacist> findByPharmacyId(@Param("pharmacyId") Long pharmacyId);

  @Query("SELECT p FROM Pharmacist p WHERE p.pharmacy.id = :pharmacyId AND p.isAdmin = true")
  List<Pharmacist> findAdminsByPharmacyId(@Param("pharmacyId") Long pharmacyId);

  boolean existsByEmail(String email);
}
