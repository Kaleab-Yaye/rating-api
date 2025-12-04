package com.rating.api.service;

import com.rating.api.entity.AuditLog;
import com.rating.api.repository.AuditLogRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuditLogService {
  private final AuditLogRepository auditLogRepository;

  public AuditLogService(AuditLogRepository auditLogRepository) {
    this.auditLogRepository = auditLogRepository;
  }

  public AuditLog createAuditLog(AuditLog auditLog) {
    return auditLogRepository.save(auditLog);
  }

  public Optional<AuditLog> getAuditLogById(Long id) {
    return auditLogRepository.findById(id);
  }

  public List<AuditLog> getAuditLogsByUserId(UUID userId) {
    return auditLogRepository.findByUserId(userId);
  }

  public List<AuditLog> getAuditLogsByMedicineId(Long medicineId) {
    return auditLogRepository.findByMedicineId(medicineId);
  }

  public List<AuditLog> getAuditLogsByBatchId(Long batchId) {
    return auditLogRepository.findByBatchId(batchId);
  }

  public List<AuditLog> getAuditLogsByOrderId(Long orderId) {
    return auditLogRepository.findByOrderId(orderId);
  }

  public List<AuditLog> getAuditLogsByAction(String action) {
    return auditLogRepository.findByAction(action);
  }

  public List<AuditLog> getAuditLogsByUserType(String userType) {
    return auditLogRepository.findByUserType(userType);
  }

  public List<AuditLog> getAuditLogsByMedicineAndBatch(Long medicineId, Long batchId) {
    return auditLogRepository.findByMedicineAndBatch(medicineId, batchId);
  }

  public List<AuditLog> getAllAuditLogs() {
    return auditLogRepository.findAll();
  }

  public void logInventoryChange(
      UUID userId,
      String userType,
      Long medicineId,
      Long batchId,
      String action,
      String changeDetails,
      Long initialState,
      Long currentState) {
    AuditLog auditLog = new AuditLog();
    auditLog.setUserId(userId);
    auditLog.setUserType(userType);
    auditLog.setAction(action);
    auditLog.setBatchChangeDetails(changeDetails);
    auditLog.setInitialBatchState(initialState);
    auditLog.setCurrentBatchState(currentState);
    auditLogRepository.save(auditLog);
  }
}
