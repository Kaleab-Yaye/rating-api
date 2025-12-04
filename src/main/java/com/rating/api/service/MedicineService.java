package com.rating.api.service;

import com.rating.api.entity.Medicine;
import com.rating.api.repository.MedBatchRepository;
import com.rating.api.repository.MedicineRepository;
import com.rating.api.repository.RatingRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MedicineService {
  private final MedicineRepository medicineRepository;
  private final MedBatchRepository medBatchRepository;
  private final RatingRepository ratingRepository;

  public MedicineService(
      MedicineRepository medicineRepository,
      MedBatchRepository medBatchRepository,
      RatingRepository ratingRepository) {
    this.medicineRepository = medicineRepository;
    this.medBatchRepository = medBatchRepository;
    this.ratingRepository = ratingRepository;
  }

  public Medicine createMedicine(Medicine medicine) {
    if (medicineRepository.findByName(medicine.getName()).isPresent()) {
      throw new RuntimeException("Medicine with name " + medicine.getName() + " already exists");
    }
    return medicineRepository.save(medicine);
  }

  public Optional<Medicine> getMedicineById(Long id) {
    return medicineRepository.findById(id);
  }

  public List<Medicine> getAllMedicines() {
    return medicineRepository.findAll();
  }

  public List<Medicine> searchMedicines(String query) {
    return medicineRepository.findByNameContainingIgnoreCase(query);
  }

  public List<Medicine> getMedicinesByMinimumRating(Integer minRating) {
    return medicineRepository.findByMinimumRating(minRating);
  }

  public List<Medicine> getMedicinesOrderByRating() {
    return medicineRepository.findAllOrderByRatingDesc();
  }

  public Long getAvailableStock(Long medicineId) {
    Long stock = medBatchRepository.calculateTotalStock(medicineId, LocalDate.now());
    return stock != null ? stock : 0L;
  }

  public boolean isStockAvailable(Long medicineId, Integer requiredQuantity) {
    Long availableStock = getAvailableStock(medicineId);
    return availableStock >= requiredQuantity;
  }

  public Medicine updateMedicine(Long id, Medicine medicineDetails) {
    Medicine medicine =
        medicineRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
    medicine.setName(medicineDetails.getName());
    medicine.setAbout(medicineDetails.getAbout());
    medicine.setPrice(medicineDetails.getPrice());
    return medicineRepository.save(medicine);
  }

  public void updateAverageRating(Long medicineId) {
    Double averageRating = ratingRepository.calculateAverageRatingByMedicineId(medicineId);
    if (averageRating != null) {
      Medicine medicine =
          medicineRepository
              .findById(medicineId)
              .orElseThrow(() -> new RuntimeException("Medicine not found"));
      medicine.setAverageRating(averageRating.intValue());
      medicineRepository.save(medicine);
    }
  }

  public void deleteMedicine(Long id) {
    Medicine medicine =
        medicineRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
    medicineRepository.delete(medicine);
  }
}
