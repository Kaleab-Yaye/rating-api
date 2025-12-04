package com.rating.api.repository;

import com.rating.api.entity.Review;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query("SELECT r FROM Review r WHERE r.medicine.id = :medicineId AND r.parentReview IS NULL")
  List<Review> findTopLevelReviewsByMedicineId(@Param("medicineId") Long medicineId);

  @Query("SELECT r FROM Review r WHERE r.parentReview.id = :parentReviewId")
  List<Review> findChildReviewsByParentId(@Param("parentReviewId") Long parentReviewId);

  @Query("SELECT r FROM Review r WHERE r.medicine.id = :medicineId")
  List<Review> findAllReviewsByMedicineId(@Param("medicineId") Long medicineId);

  @Query("SELECT r FROM Review r WHERE r.pharmacy.id = :pharmacyId")
  List<Review> findByPharmacyId(@Param("pharmacyId") Long pharmacyId);

  @Query("SELECT r FROM Review r WHERE r.pharmacist.id = :pharmacistId")
  List<Review> findByPharmacistId(@Param("pharmacistId") UUID pharmacistId);

  Optional<Review> findByRatingId(Long ratingId);
}
