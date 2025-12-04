package com.rating.api.repository;

import com.rating.api.entity.Rating;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

  @Query("SELECT r FROM Rating r WHERE r.medicine.id = :medicineId")
  List<Rating> findByMedicineId(@Param("medicineId") Long medicineId);

  @Query("SELECT r FROM Rating r WHERE r.pharmacy.id = :pharmacyId")
  List<Rating> findByPharmacyId(@Param("pharmacyId") Long pharmacyId);

  @Query("SELECT r FROM Rating r WHERE r.pharmacist.id = :pharmacistId")
  List<Rating> findByPharmacistId(@Param("pharmacistId") UUID pharmacistId);

  @Query("SELECT r FROM Rating r WHERE r.medicine.id = :medicineId AND r.pharmacy.id = :pharmacyId")
  Optional<Rating> findByMedicineIdAndPharmacyId(
      @Param("medicineId") Long medicineId, @Param("pharmacyId") Long pharmacyId);

  @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.medicine.id = :medicineId")
  Double calculateAverageRatingByMedicineId(@Param("medicineId") Long medicineId);
}
