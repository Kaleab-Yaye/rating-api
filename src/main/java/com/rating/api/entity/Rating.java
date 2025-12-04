package com.rating.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {
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

  @Column(name = "rating", nullable = false)
  private Integer rating;

  @OneToOne(mappedBy = "rating", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Review review;

  public Rating() {}

  public Rating(Pharmacist pharmacist, Pharmacy pharmacy, Medicine medicine, Integer rating) {
    this.pharmacist = pharmacist;
    this.pharmacy = pharmacy;
    this.medicine = medicine;
    this.rating = rating;
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

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public Review getReview() {
    return review;
  }

  public void setReview(Review review) {
    this.review = review;
  }
}
