package com.rating.api.dto;

import java.util.List;
import java.util.UUID;

public class CreateOrderRequestDTO {
  private UUID pharmacistId;
  private Long pharmacyId;
  private List<OrderItemDTO> items;

  public CreateOrderRequestDTO() {}

  public CreateOrderRequestDTO(UUID pharmacistId, Long pharmacyId, List<OrderItemDTO> items) {
    this.pharmacistId = pharmacistId;
    this.pharmacyId = pharmacyId;
    this.items = items;
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

  public List<OrderItemDTO> getItems() {
    return items;
  }

  public void setItems(List<OrderItemDTO> items) {
    this.items = items;
  }
}

class OrderItemDTO {
  private Long medicineId;
  private Integer quantity;

  public OrderItemDTO() {}

  public OrderItemDTO(Long medicineId, Integer quantity) {
    this.medicineId = medicineId;
    this.quantity = quantity;
  }

  public Long getMedicineId() {
    return medicineId;
  }

  public void setMedicineId(Long medicineId) {
    this.medicineId = medicineId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
