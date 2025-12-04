package com.rating.api.controller;

import com.rating.api.entity.Rating;
import com.rating.api.service.RatingService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
  private final RatingService ratingService;

  public RatingController(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @PostMapping
  public ResponseEntity<?> createRating(@RequestBody Rating rating) {
    try {
      Rating createdRating = ratingService.createRating(rating);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getRatingById(@PathVariable Long id) {
    return ratingService
        .getRatingById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/medicine/{medicineId}")
  public ResponseEntity<List<Rating>> getRatingsByMedicine(@PathVariable Long medicineId) {
    List<Rating> ratings = ratingService.getRatingsByMedicineId(medicineId);
    return ResponseEntity.ok(ratings);
  }

  @GetMapping("/pharmacy/{pharmacyId}")
  public ResponseEntity<List<Rating>> getRatingsByPharmacy(@PathVariable Long pharmacyId) {
    List<Rating> ratings = ratingService.getRatingsByPharmacyId(pharmacyId);
    return ResponseEntity.ok(ratings);
  }

  @GetMapping("/pharmacist/{pharmacistId}")
  public ResponseEntity<List<Rating>> getRatingsByPharmacist(@PathVariable UUID pharmacistId) {
    List<Rating> ratings = ratingService.getRatingsByPharmacistId(pharmacistId);
    return ResponseEntity.ok(ratings);
  }

  @GetMapping("/medicine/{medicineId}/average")
  public ResponseEntity<Map<String, Object>> getAverageRatingForMedicine(
      @PathVariable Long medicineId) {
    Double averageRating = ratingService.getAverageRatingForMedicine(medicineId);
    Map<String, Object> response =
        Map.of(
            "medicineId", medicineId, "averageRating", averageRating != null ? averageRating : 0.0);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateRating(
      @PathVariable Long id, @RequestBody Map<String, Integer> ratingUpdate) {
    try {
      Integer newRating = ratingUpdate.get("rating");
      Rating updatedRating = ratingService.updateRating(id, newRating);
      return ResponseEntity.ok(updatedRating);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
    try {
      ratingService.deleteRating(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
