package com.rating.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter @Setter Long id;

  @ManyToOne
  @JoinColumn(name = "pharmacist_id", nullable = false)
  private @Getter @Setter Pharmacist pharmacist;

  @ManyToOne
  @JoinColumn(name = "pharmacy_id", nullable = false)
  private @Getter @Setter Pharmacy pharmacy;

  @ManyToOne
  @JoinColumn(name = "medicines_id", nullable = false)
  private @Getter @Setter Medicine medicine;

  @ManyToOne
  @JoinColumn(name = "rate_id")
  private @Getter @Setter Rating rating;

  @ManyToOne
  @JoinColumn(name = "parent_review_id")
  private @Getter @Setter Review parentReview;

  @Column(nullable = false, columnDefinition = "TEXT")
  private @Getter @Setter String review;
}
