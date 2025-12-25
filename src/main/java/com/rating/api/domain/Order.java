package com.rating.api.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter @Setter Long id;

  @ManyToOne
  @JoinColumn(name = "pharmacist_id", nullable = false)
  private @Getter @Setter Pharmacist pharmacist;

  @ManyToOne
  @JoinColumn(name = "pharmacy_id", nullable = false)
  private @Getter @Setter Pharmacy pharmacy;

  @Column(name = "order_date")
  private @Getter @Setter OffsetDateTime orderDate;

  @Column(name = "order_delivered_date")
  private @Getter @Setter OffsetDateTime orderDeliveredDate;

  @Column(name = "order_closed")
  private @Getter @Setter Boolean orderClosed = false;

  @Version private @Getter @Setter Integer version;
}
