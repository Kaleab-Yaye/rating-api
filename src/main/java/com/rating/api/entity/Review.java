package com.rating.api.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reviews")
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pharmacist_id", nullable = false)
  private Pharmacist pharmacist;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pharmacy_id", nullable = false)
  private Pharmacy pharmacy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "medicines_id", nullable = false)
  private Medicine medicine;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rate_id", unique = true)
  private Rating rating;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_review_id")
  private Review parentReview;

  @OneToMany(mappedBy = "parentReview", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Review> childReviews = new ArrayList<>();

  @Column(name = "review", nullable = false, columnDefinition = "TEXT")
  private String reviewText;

  public Review() {}

  // For review with rating
  public Review(
      Pharmacist pharmacist,
      Pharmacy pharmacy,
      Medicine medicine,
      Rating rating,
      String reviewText) {
    this.pharmacist = pharmacist;
    this.pharmacy = pharmacy;
    this.medicine = medicine;
    this.rating = rating;
    this.reviewText = reviewText;
  }

  // For child review (comment on existing review)
  public Review(
      Pharmacist pharmacist,
      Pharmacy pharmacy,
      Medicine medicine,
      Review parentReview,
      String reviewText) {
    this.pharmacist = pharmacist;
    this.pharmacy = pharmacy;
    this.medicine = medicine;
    this.parentReview = parentReview;
    this.reviewText = reviewText;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Pharmacist getPharmacist() {
    return pharmacist;
  }

  public void setPharmacist(Pharmacist pharmacist) {
    this.pharmacist = pharmacist;
  }

  public Pharmacy getPharmacy() {
    return pharmacy;
  }

  public void setPharmacy(Pharmacy pharmacy) {
    this.pharmacy = pharmacy;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public void setMedicine(Medicine medicine) {
    this.medicine = medicine;
  }

  public Rating getRating() {
    return rating;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }

  public Review getParentReview() {
    return parentReview;
  }

  public void setParentReview(Review parentReview) {
    this.parentReview = parentReview;
  }

  public List<Review> getChildReviews() {
    return childReviews;
  }

  public void setChildReviews(List<Review> childReviews) {
    this.childReviews = childReviews;
  }

  public String getReviewText() {
    return reviewText;
  }

  public void setReviewText(String reviewText) {
    this.reviewText = reviewText;
  }
}
