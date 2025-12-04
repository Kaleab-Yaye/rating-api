package com.rating.api.repository;

import com.rating.api.entity.MedBatch;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedBatchRepository extends JpaRepository<MedBatch, Long> {

  @Query(
      "SELECT mb FROM MedBatch mb WHERE mb.medicine.id = :medicineId AND mb.expiry > :currentDate")
  List<MedBatch> findValidBatchesByMedicineId(
      @Param("medicineId") Long medicineId, @Param("currentDate") LocalDate currentDate);

  @Query(
      "SELECT SUM(mb.amountPresent) FROM MedBatch mb WHERE mb.medicine.id = :medicineId AND mb.expiry > :currentDate")
  Long calculateTotalStock(
      @Param("medicineId") Long medicineId, @Param("currentDate") LocalDate currentDate);

  List<MedBatch> findByExpiryBefore(LocalDate date);

  @Query("SELECT mb FROM MedBatch mb WHERE mb.expiry BETWEEN :startDate AND :endDate")
  List<MedBatch> findBatchesExpiringBetween(
      @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
