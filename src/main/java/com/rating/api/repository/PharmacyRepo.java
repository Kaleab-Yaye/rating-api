package com.rating.api.repository;

import com.rating.api.domain.Pharmacy;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepo extends CrudRepository<Pharmacy, Integer> {
  Optional<Pharmacy> findPharmacyByPharmacyName(String pharmacyName);

  Optional<Pharmacy> findPharmacyByPharmacyId(Long pharmacyId);
}
