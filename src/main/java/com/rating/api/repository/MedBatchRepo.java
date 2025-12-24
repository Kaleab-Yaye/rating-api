package com.rating.api.repository;

import com.rating.api.domain.MedBatch;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedBatchRepo extends JpaRepository<MedBatch, Integer> {
  Optional<MedBatch> findMedBatchById(int id);
}
