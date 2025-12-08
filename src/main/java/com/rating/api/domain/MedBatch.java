package com.rating.api.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "med_batches")
public class MedBatch {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter @Setter Long id;

  @ManyToOne
  @JoinColumn(name = "med_id")
  private @Getter @Setter Medicine medicine;

  @Column(name = "amount_present")
  private @Getter @Setter Long amountPresent;

  private @Getter @Setter LocalDate expiry;

  @Version private @Getter @Setter Integer version;
}
