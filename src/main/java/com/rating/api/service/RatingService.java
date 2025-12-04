package com.rating.api.service;

import com.rating.api.entity.Medicine;
import com.rating.api.entity.Pharmacist;
import com.rating.api.entity.Pharmacy;
import com.rating.api.entity.Rating;
import com.rating.api.repository.MedicineRepository;
import com.rating.api.repository.PharmacistRepository;
import com.rating.api.repository.PharmacyRepository;
import com.rating.api.repository.RatingRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RatingService {
  private final RatingRepository ratingRepository;
  private final PharmacistRepository pharmacistRepository;
  private final PharmacyRepository pharmacyRepository;
  private final MedicineRepository medicineRepository;
  private final MedicineService medicineService;

  public RatingService(
      RatingRepository ratingRepository,
      PharmacistRepository pharmacistRepository,
      PharmacyRepository pharmacyRepository,
      MedicineRepository medicineRepository,
      MedicineService medicineService) {
    this.ratingRepository = ratingRepository;
    this.pharmacistRepository = pharmacistRepository;
    this.pharmacyRepository = pharmacyRepository;
    this.medicineRepository = medicineRepository;
    this.medicineService = medicineService;
  }

  public Rating createRating(Rating rating) {
    Pharmacist pharmacist =
        pharmacistRepository
            .findById(rating.getPharmacist().getId())
            .orElseThrow(() -> new RuntimeException("Pharmacist not found"));
    Pharmacy pharmacy =
        pharmacyRepository
            .findById(rating.getPharmacy().getId())
            .orElseThrow(() -> new RuntimeException("Pharmacy not found"));
    Medicine medicine =
        medicineRepository
            .findById(rating.getMedicine().getId())
            .orElseThrow(() -> new RuntimeException("Medicine not found"));

    // Check if rating already exists for this combination
    Optional<Rating> existingRating =
        ratingRepository.findByMedicineIdAndPharmacyId(medicine.getId(), pharmacy.getId());

    if (existingRating.isPresent()) {
      throw new RuntimeException("Rating already exists for this medicine and pharmacy");
    }

    // Validate rating value
    if (rating.getRating() < 1 || rating.getRating() > 5) {
      throw new RuntimeException("Rating must be between 1 and 5");
    }

    rating.setPharmacist(pharmacist);
    rating.setPharmacy(pharmacy);
    rating.setMedicine(medicine);

    Rating savedRating = ratingRepository.save(rating);

    // Update medicine's average rating
    medicineService.updateAverageRating(medicine.getId());

    return savedRating;
  }

  public Optional<Rating> getRatingById(Long id) {
    return ratingRepository.findById(id);
  }

  public List<Rating> getRatingsByMedicineId(Long medicineId) {
    return ratingRepository.findByMedicineId(medicineId);
  }

  public List<Rating> getRatingsByPharmacyId(Long pharmacyId) {
    return ratingRepository.findByPharmacyId(pharmacyId);
  }

  public List<Rating> getRatingsByPharmacistId(UUID pharmacistId) {
    return ratingRepository.findByPharmacistId(pharmacistId);
  }

  public Double getAverageRatingForMedicine(Long medicineId) {
    return ratingRepository.calculateAverageRatingByMedicineId(medicineId);
  }

  public Rating updateRating(Long id, Integer newRating) {
    Rating rating =
        ratingRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Rating not found with id: " + id));

    if (newRating < 1 || newRating > 5) {
      throw new RuntimeException("Rating must be between 1 and 5");
    }

    rating.setRating(newRating);
    Rating updatedRating = ratingRepository.save(rating);

    // Update medicine's average rating
    medicineService.updateAverageRating(rating.getMedicine().getId());

    return updatedRating;
  }

  public void deleteRating(Long id) {
    Rating rating =
        ratingRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Rating not found with id: " + id));
    Long medicineId = rating.getMedicine().getId();
    ratingRepository.delete(rating);

    // Update medicine's average rating
    medicineService.updateAverageRating(medicineId);
  }
}
