package com.rating.api.dto;

import java.util.UUID;

public class RatingRequestDTO {
  private UUID pharmacistId;
  private Long pharmacyId;
  private Long medicineId;
  private Integer rating;

  public RatingRequestDTO() {}

  public RatingRequestDTO(UUID pharmacistId, Long pharmacyId, Long medicineId, Integer rating) {
    this.pharmacistId = pharmacistId;
    this.pharmacyId = pharmacyId;
    this.medicineId = medicineId;
    this.rating = rating;
  }

  public UUID getPharmacistId() {
    return pharmacistId;
  }

  public void setPharmacistId(UUID pharmacistId) {
    this.pharmacistId = pharmacistId;
  }

  public Long getPharmacyId() {
    return pharmacyId;
  }

  public void setPharmacyId(Long pharmacyId) {
    this.pharmacyId = pharmacyId;
  }

  public Long getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }
}
