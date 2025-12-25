package com.rating.api.repository;

import com.rating.api.domain.MedBatch;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedBatchRepo extends JpaRepository<MedBatch, Integer> {
  Optional<MedBatch> findMedBatchById(int id);

  @Query(
      value = "SELECT SUM(amount_present) FROM med_batches WHERE expiry > :exDay AND id = :id",
      nativeQuery = true)
  Optional<Long> getAmountPresent(@Param("id") Long id, @Param("exDay") LocalDate exDay);

  @Query(
      value = "SELECT * From med_batches WHERE expiry > :exDay AND id = :id ORDER BY expiry ASC",
      nativeQuery = true)
  List<MedBatch> findEnoughBatches(
      @Param("id") Long id, @Param("exDay") LocalDate exDay, Pageable pageable);
}
