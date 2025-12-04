package com.rating.api.dto;

public class MedicineDTO {
  private Long id;
  private String name;
  private String about;
  private Long price;
  private Integer averageRating;
  private Long availableStock;

  public MedicineDTO() {}

  public MedicineDTO(
      Long id, String name, String about, Long price, Integer averageRating, Long availableStock) {
    this.id = id;
    this.name = name;
    this.about = about;
    this.price = price;
    this.averageRating = averageRating;
    this.availableStock = availableStock;
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

  public Long getAvailableStock() {
    return availableStock;
  }

  public void setAvailableStock(Long availableStock) {
    this.availableStock = availableStock;
  }
}
