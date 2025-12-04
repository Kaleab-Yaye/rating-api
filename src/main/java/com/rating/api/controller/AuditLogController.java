package com.rating.api.controller;

import com.rating.api.entity.AuditLog;
import com.rating.api.service.AuditLogService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {
  private final AuditLogService auditLogService;

  public AuditLogController(AuditLogService auditLogService) {
    this.auditLogService = auditLogService;
  }

  @GetMapping
  public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
    List<AuditLog> auditLogs = auditLogService.getAllAuditLogs();
    return ResponseEntity.ok(auditLogs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getAuditLogById(@PathVariable Long id) {
    return auditLogService
        .getAuditLogById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<AuditLog>> getAuditLogsByUser(@PathVariable UUID userId) {
    List<AuditLog> auditLogs = auditLogService.getAuditLogsByUserId(userId);
    return ResponseEntity.ok(auditLogs);
  }

  @GetMapping("/medicine/{medicineId}")
  public ResponseEntity<List<AuditLog>> getAuditLogsByMedicine(@PathVariable Long medicineId) {
    List<AuditLog> auditLogs = auditLogService.getAuditLogsByMedicineId(medicineId);
    return ResponseEntity.ok(auditLogs);
  }

  @GetMapping("/batch/{batchId}")
  public ResponseEntity<List<AuditLog>> getAuditLogsByBatch(@PathVariable Long batchId) {
    List<AuditLog> auditLogs = auditLogService.getAuditLogsByBatchId(batchId);
    return ResponseEntity.ok(auditLogs);
  }

  @GetMapping("/order/{orderId}")
  public ResponseEntity<List<AuditLog>> getAuditLogsByOrder(@PathVariable Long orderId) {
    List<AuditLog> auditLogs = auditLogService.getAuditLogsByOrderId(orderId);
    return ResponseEntity.ok(auditLogs);
  }

  @GetMapping("/action/{action}")
  public ResponseEntity<List<AuditLog>> getAuditLogsByAction(@PathVariable String action) {
    List<AuditLog> auditLogs = auditLogService.getAuditLogsByAction(action);
    return ResponseEntity.ok(auditLogs);
  }

  @GetMapping("/user-type/{userType}")
  public ResponseEntity<List<AuditLog>> getAuditLogsByUserType(@PathVariable String userType) {
    List<AuditLog> auditLogs = auditLogService.getAuditLogsByUserType(userType);
    return ResponseEntity.ok(auditLogs);
  }
}
