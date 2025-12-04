package com.rating.api.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "user_type", length = 255)
  private String userType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "med_id")
  private Medicine medicine;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "batch_id")
  private MedBatch batch;

  @Column(name = "action", length = 255)
  private String action;

  @Column(name = "batch_change_details", columnDefinition = "JSONB")
  private String batchChangeDetails;

  @Column(name = "initial_batch_state")
  private Long initialBatchState;

  @Column(name = "current_batch_state")
  private Long currentBatchState;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @Version private Long version;

  public AuditLog() {}

  public AuditLog(
      UUID userId,
      String userType,
      Medicine medicine,
      MedBatch batch,
      String action,
      String batchChangeDetails,
      Long initialBatchState,
      Long currentBatchState) {
    this.userId = userId;
    this.userType = userType;
    this.medicine = medicine;
    this.batch = batch;
    this.action = action;
    this.batchChangeDetails = batchChangeDetails;
    this.initialBatchState = initialBatchState;
    this.currentBatchState = currentBatchState;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public void setMedicine(Medicine medicine) {
    this.medicine = medicine;
  }

  public MedBatch getBatch() {
    return batch;
  }

  public void setBatch(MedBatch batch) {
    this.batch = batch;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getBatchChangeDetails() {
    return batchChangeDetails;
  }

  public void setBatchChangeDetails(String batchChangeDetails) {
    this.batchChangeDetails = batchChangeDetails;
  }

  public Long getInitialBatchState() {
    return initialBatchState;
  }

  public void setInitialBatchState(Long initialBatchState) {
    this.initialBatchState = initialBatchState;
  }

  public Long getCurrentBatchState() {
    return currentBatchState;
  }

  public void setCurrentBatchState(Long currentBatchState) {
    this.currentBatchState = currentBatchState;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
