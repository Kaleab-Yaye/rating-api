package com.rating.api.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medicines")
public class Medicine {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", unique = true, nullable = false, length = 255)
  private String name;

  @Column(name = "about", columnDefinition = "TEXT")
  private String about;

  @Column(name = "price")
  private Long price;

  @Column(name = "average_rating")
  private Integer averageRating = 0;

  @Version private Long version;

  @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<MedBatch> batches = new ArrayList<>();

  @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Rating> ratings = new ArrayList<>();

  @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Review> reviews = new ArrayList<>();

  public Medicine() {}

  public Medicine(String name, String about, Long price) {
    this.name = name;
    this.about = about;
    this.price = price;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public Integer getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(Integer averageRating) {
    this.averageRating = averageRating;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public List<MedBatch> getBatches() {
    return batches;
  }

  public void setBatches(List<MedBatch> batches) {
    this.batches = batches;
  }

  public List<Rating> getRatings() {
    return ratings;
  }

  public void setRatings(List<Rating> ratings) {
    this.ratings = ratings;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }
}
