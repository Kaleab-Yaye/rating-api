package com.rating.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pharmacies")
public class Pharmacy {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is required")
  @Column(name = "name", unique = true, nullable = false, length = 255)
  private String name;

  @NotBlank(message = "Street address is required")
  @Column(name = "street_address", nullable = false, length = 255)
  private String streetAddress;

  @NotBlank(message = "City is required")
  @Column(name = "city", nullable = false, length = 100)
  private String city;

  @NotBlank(message = "Region is required")
  @Column(name = "region", nullable = false, length = 100)
  private String region;

  @Column(name = "postal_code", length = 20)
  private String postalCode;

  @NotNull(message = "Balance is required")
  @Column(name = "balance")
  private Long balance = 100L;

  @Version private Long version;

  @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Pharmacist> pharmacists = new ArrayList<>();

  @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Order> orders = new ArrayList<>();

  @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Rating> ratings = new ArrayList<>();

  @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Review> reviews = new ArrayList<>();

  public Pharmacy() {}

  public Pharmacy(
      String name, String streetAddress, String city, String region, String postalCode) {
    this.name = name;
    this.streetAddress = streetAddress;
    this.city = city;
    this.region = region;
    this.postalCode = postalCode;
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

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public Long getBalance() {
    return balance;
  }

  public void setBalance(Long balance) {
    this.balance = balance;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public List<Pharmacist> getPharmacists() {
    return pharmacists;
  }

  public void setPharmacists(List<Pharmacist> pharmacists) {
    this.pharmacists = pharmacists;
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
