package com.rating.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_list")
public class OrderList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "medicines_id", nullable = false)
  private Medicine medicine;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(name = "amount_ordered")
  private Integer amountOrdered;

  @Version private Long version;

  public OrderList() {}

  public OrderList(Medicine medicine, Order order, Integer amountOrdered) {
    this.medicine = medicine;
    this.order = order;
    this.amountOrdered = amountOrdered;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public void setMedicine(Medicine medicine) {
    this.medicine = medicine;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Integer getAmountOrdered() {
    return amountOrdered;
  }

  public void setAmountOrdered(Integer amountOrdered) {
    this.amountOrdered = amountOrdered;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
