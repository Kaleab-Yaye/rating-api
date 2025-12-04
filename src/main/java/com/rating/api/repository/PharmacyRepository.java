package com.rating.api.repository;

import com.rating.api.entity.Pharmacy;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
  Optional<Pharmacy> findByName(String name);

  @Query("SELECT p FROM Pharmacy p WHERE p.city = :city")
  List<Pharmacy> findByCity(@Param("city") String city);

  @Query("SELECT p FROM Pharmacy p WHERE p.region = :region")
  List<Pharmacy> findByRegion(@Param("region") String region);

  @Query("SELECT p FROM Pharmacy p WHERE p.balance < :minBalance")
  List<Pharmacy> findWithLowBalance(@Param("minBalance") Long minBalance);
}
