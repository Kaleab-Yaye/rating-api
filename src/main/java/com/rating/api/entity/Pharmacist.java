package com.rating.api.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pharmacists")
public class Pharmacist {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "name", nullable = false, unique = true, length = 255)
  private String name;

  @Column(name = "email", nullable = false, unique = true, length = 255)
  private String email;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pharmacy_id", nullable = false)
  private Pharmacy pharmacy;

  @Column(name = "is_admin")
  private Boolean isAdmin = false;

  @Version private Long version;

  @OneToMany(mappedBy = "pharmacist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Order> orders = new ArrayList<>();

  @OneToMany(mappedBy = "pharmacist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Rating> ratings = new ArrayList<>();

  @OneToMany(mappedBy = "pharmacist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Review> reviews = new ArrayList<>();

  public Pharmacist() {}

  public Pharmacist(String name, String email, Pharmacy pharmacy, Boolean isAdmin) {
    this.name = name;
    this.email = email;
    this.pharmacy = pharmacy;
    this.isAdmin = isAdmin;
  }

  // Getters and setters
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Pharmacy getPharmacy() {
    return pharmacy;
  }

  public void setPharmacy(Pharmacy pharmacy) {
    this.pharmacy = pharmacy;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
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
