package com.rating.api.dto;

import java.util.UUID;

public class ReviewRequestDTO {
  private UUID pharmacistId;
  private Long pharmacyId;
  private Long medicineId;
  private Long ratingId;
  private Long parentReviewId;
  private String reviewText;

  public ReviewRequestDTO() {}

  public ReviewRequestDTO(
      UUID pharmacistId,
      Long pharmacyId,
      Long medicineId,
      Long ratingId,
      Long parentReviewId,
      String reviewText) {
    this.pharmacistId = pharmacistId;
    this.pharmacyId = pharmacyId;
    this.medicineId = medicineId;
    this.ratingId = ratingId;
    this.parentReviewId = parentReviewId;
    this.reviewText = reviewText;
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

  public Long getRatingId() {
    return ratingId;
  }

  public void setRatingId(Long ratingId) {
    this.ratingId = ratingId;
  }

  public Long getParentReviewId() {
    return parentReviewId;
  }

  public void setParentReviewId(Long parentReviewId) {
    this.parentReviewId = parentReviewId;
  }

  public String getReviewText() {
    return reviewText;
  }

  public void setReviewText(String reviewText) {
    this.reviewText = reviewText;
  }
}
