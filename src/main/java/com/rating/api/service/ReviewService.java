package com.rating.api.service;

import com.rating.api.entity.Medicine;
import com.rating.api.entity.Pharmacist;
import com.rating.api.entity.Pharmacy;
import com.rating.api.entity.Rating;
import com.rating.api.entity.Review;
import com.rating.api.repository.MedicineRepository;
import com.rating.api.repository.PharmacistRepository;
import com.rating.api.repository.PharmacyRepository;
import com.rating.api.repository.RatingRepository;
import com.rating.api.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final RatingRepository ratingRepository;
  private final PharmacistRepository pharmacistRepository;
  private final PharmacyRepository pharmacyRepository;
  private final MedicineRepository medicineRepository;

  public ReviewService(
      ReviewRepository reviewRepository,
      RatingRepository ratingRepository,
      PharmacistRepository pharmacistRepository,
      PharmacyRepository pharmacyRepository,
      MedicineRepository medicineRepository) {
    this.reviewRepository = reviewRepository;
    this.ratingRepository = ratingRepository;
    this.pharmacistRepository = pharmacistRepository;
    this.pharmacyRepository = pharmacyRepository;
    this.medicineRepository = medicineRepository;
  }

  public Review createReviewWithRating(Review review) {
    Pharmacist pharmacist =
        pharmacistRepository
            .findById(review.getPharmacist().getId())
            .orElseThrow(() -> new RuntimeException("Pharmacist not found"));
    Pharmacy pharmacy =
        pharmacyRepository
            .findById(review.getPharmacy().getId())
            .orElseThrow(() -> new RuntimeException("Pharmacy not found"));
    Medicine medicine =
        medicineRepository
            .findById(review.getMedicine().getId())
            .orElseThrow(() -> new RuntimeException("Medicine not found"));

    // Verify rating exists and belongs to the same entities
    Rating rating =
        ratingRepository
            .findById(review.getRating().getId())
            .orElseThrow(() -> new RuntimeException("Rating not found"));

    if (!rating.getPharmacist().getId().equals(pharmacist.getId())
        || !rating.getPharmacy().getId().equals(pharmacy.getId())
        || !rating.getMedicine().getId().equals(medicine.getId())) {
      throw new RuntimeException(
          "Rating does not match the provided pharmacist, pharmacy, and medicine");
    }

    // Check if review already exists for this rating
    if (reviewRepository.findByRatingId(rating.getId()).isPresent()) {
      throw new RuntimeException("Review already exists for this rating");
    }

    review.setPharmacist(pharmacist);
    review.setPharmacy(pharmacy);
    review.setMedicine(medicine);
    review.setRating(rating);

    return reviewRepository.save(review);
  }

  public Review createChildReview(Review review, Long parentReviewId) {
    Pharmacist pharmacist =
        pharmacistRepository
            .findById(review.getPharmacist().getId())
            .orElseThrow(() -> new RuntimeException("Pharmacist not found"));
    Pharmacy pharmacy =
        pharmacyRepository
            .findById(review.getPharmacy().getId())
            .orElseThrow(() -> new RuntimeException("Pharmacy not found"));
    Medicine medicine =
        medicineRepository
            .findById(review.getMedicine().getId())
            .orElseThrow(() -> new RuntimeException("Medicine not found"));

    Review parentReview =
        reviewRepository
            .findById(parentReviewId)
            .orElseThrow(() -> new RuntimeException("Parent review not found"));

    // Verify parent review belongs to the same medicine
    if (!parentReview.getMedicine().getId().equals(medicine.getId())) {
      throw new RuntimeException("Parent review does not belong to the same medicine");
    }

    review.setPharmacist(pharmacist);
    review.setPharmacy(pharmacy);
    review.setMedicine(medicine);
    review.setParentReview(parentReview);

    return reviewRepository.save(review);
  }

  public Optional<Review> getReviewById(Long id) {
    return reviewRepository.findById(id);
  }

  public List<Review> getTopLevelReviewsByMedicineId(Long medicineId) {
    return reviewRepository.findTopLevelReviewsByMedicineId(medicineId);
  }

  public List<Review> getChildReviewsByParentId(Long parentReviewId) {
    return reviewRepository.findChildReviewsByParentId(parentReviewId);
  }

  public List<Review> getAllReviewsByMedicineId(Long medicineId) {
    return reviewRepository.findAllReviewsByMedicineId(medicineId);
  }

  public List<Review> getReviewsByPharmacyId(Long pharmacyId) {
    return reviewRepository.findByPharmacyId(pharmacyId);
  }

  public List<Review> getReviewsByPharmacistId(UUID pharmacistId) {
    return reviewRepository.findByPharmacistId(pharmacistId);
  }

  public Review updateReview(Long id, String newReviewText) {
    Review review =
        reviewRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    review.setReviewText(newReviewText);
    return reviewRepository.save(review);
  }

  public void deleteReview(Long id) {
    Review review =
        reviewRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    reviewRepository.delete(review);
  }
}
