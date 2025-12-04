package com.rating.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pharmacist_id", nullable = false)
  private Pharmacist pharmacist;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pharmacy_id", nullable = false)
  private Pharmacy pharmacy;

  @Column(name = "order_date")
  private LocalDateTime orderDate = LocalDateTime.now();

  @Column(name = "order_delivered_date")
  private LocalDateTime orderDeliveredDate;

  @Column(name = "order_closed")
  private Boolean orderClosed = false;

  @Version private Long version;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<OrderList> orderItems = new ArrayList<>();

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<AuditLog> auditLogs = new ArrayList<>();

  public Order() {}

  public Order(Pharmacist pharmacist, Pharmacy pharmacy) {
    this.pharmacist = pharmacist;
    this.pharmacy = pharmacy;
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

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public LocalDateTime getOrderDeliveredDate() {
    return orderDeliveredDate;
  }

  public void setOrderDeliveredDate(LocalDateTime orderDeliveredDate) {
    this.orderDeliveredDate = orderDeliveredDate;
  }

  public Boolean getOrderClosed() {
    return orderClosed;
  }

  public void setOrderClosed(Boolean orderClosed) {
    this.orderClosed = orderClosed;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public List<OrderList> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderList> orderItems) {
    this.orderItems = orderItems;
  }

  public List<AuditLog> getAuditLogs() {
    return auditLogs;
  }

  public void setAuditLogs(List<AuditLog> auditLogs) {
    this.auditLogs = auditLogs;
  }
}
