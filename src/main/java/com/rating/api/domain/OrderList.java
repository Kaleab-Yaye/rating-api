package com.rating.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_list")
public class OrderList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter @Setter Long id;

  @ManyToOne
  @JoinColumn(name = "medicines_id", nullable = false)
  private @Getter @Setter Medicine medicine;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private @Getter @Setter Order order;

  @Column(name = "amount_ordered")
  private @Getter @Setter Integer amountOrdered;

  @Version private @Getter @Setter Integer version;
}
