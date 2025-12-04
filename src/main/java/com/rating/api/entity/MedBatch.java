package com.rating.api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "med_batches")
public class MedBatch {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "med_id", nullable = false)
  private Medicine medicine;

  @Column(name = "amount_present")
  private Long amountPresent;

  @Column(name = "expiry")
  private LocalDate expiry;

  @Version private Long version;

  @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<AuditLog> auditLogs = new ArrayList<>();

  public MedBatch() {}

  public MedBatch(Medicine medicine, Long amountPresent, LocalDate expiry) {
    this.medicine = medicine;
    this.amountPresent = amountPresent;
    this.expiry = expiry;
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

  public Long getAmountPresent() {
    return amountPresent;
  }

  public void setAmountPresent(Long amountPresent) {
    this.amountPresent = amountPresent;
  }

  public LocalDate getExpiry() {
    return expiry;
  }

  public void setExpiry(LocalDate expiry) {
    this.expiry = expiry;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public List<AuditLog> getAuditLogs() {
    return auditLogs;
  }

  public void setAuditLogs(List<AuditLog> auditLogs) {
    this.auditLogs = auditLogs;
  }
}
