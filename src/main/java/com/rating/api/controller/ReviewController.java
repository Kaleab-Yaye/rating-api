package com.rating.api.controller;

import com.rating.api.entity.Review;
import com.rating.api.service.ReviewService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping
  public ResponseEntity<?> createReviewWithRating(@RequestBody Review review) {
    try {
      Review createdReview = reviewService.createReviewWithRating(review);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @PostMapping("/{parentReviewId}/reply")
  public ResponseEntity<?> createChildReview(
      @PathVariable Long parentReviewId, @RequestBody Review review) {
    try {
      Review createdReview = reviewService.createChildReview(review, parentReviewId);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getReviewById(@PathVariable Long id) {
    return reviewService
        .getReviewById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/medicine/{medicineId}/top-level")
  public ResponseEntity<List<Review>> getTopLevelReviewsByMedicine(@PathVariable Long medicineId) {
    List<Review> reviews = reviewService.getTopLevelReviewsByMedicineId(medicineId);
    return ResponseEntity.ok(reviews);
  }

  @GetMapping("/{parentReviewId}/replies")
  public ResponseEntity<List<Review>> getChildReviews(@PathVariable Long parentReviewId) {
    List<Review> reviews = reviewService.getChildReviewsByParentId(parentReviewId);
    return ResponseEntity.ok(reviews);
  }

  @GetMapping("/medicine/{medicineId}/all")
  public ResponseEntity<List<Review>> getAllReviewsByMedicine(@PathVariable Long medicineId) {
    List<Review> reviews = reviewService.getAllReviewsByMedicineId(medicineId);
    return ResponseEntity.ok(reviews);
  }

  @GetMapping("/pharmacy/{pharmacyId}")
  public ResponseEntity<List<Review>> getReviewsByPharmacy(@PathVariable Long pharmacyId) {
    List<Review> reviews = reviewService.getReviewsByPharmacyId(pharmacyId);
    return ResponseEntity.ok(reviews);
  }

  @GetMapping("/pharmacist/{pharmacistId}")
  public ResponseEntity<List<Review>> getReviewsByPharmacist(@PathVariable UUID pharmacistId) {
    List<Review> reviews = reviewService.getReviewsByPharmacistId(pharmacistId);
    return ResponseEntity.ok(reviews);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateReview(
      @PathVariable Long id, @RequestBody Map<String, String> reviewUpdate) {
    try {
      String newReviewText = reviewUpdate.get("reviewText");
      Review updatedReview = reviewService.updateReview(id, newReviewText);
      return ResponseEntity.ok(updatedReview);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
    try {
      reviewService.deleteReview(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
