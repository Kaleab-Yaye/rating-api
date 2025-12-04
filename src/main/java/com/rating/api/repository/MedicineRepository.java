package com.rating.api.repository;

import com.rating.api.entity.Medicine;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
  Optional<Medicine> findByName(String name);

  List<Medicine> findByNameContainingIgnoreCase(String name);

  @Query("SELECT m FROM Medicine m WHERE m.averageRating >= :minRating")
  List<Medicine> findByMinimumRating(@Param("minRating") Integer minRating);

  @Query("SELECT m FROM Medicine m ORDER BY m.averageRating DESC")
  List<Medicine> findAllOrderByRatingDesc();
}
