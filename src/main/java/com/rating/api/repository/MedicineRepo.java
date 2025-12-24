package com.rating.api.repository;

import com.rating.api.domain.Medicine;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepo extends JpaRepository<MedicineRepo, Long> {
  Optional<Medicine> findMedicineById(Long id);
}
