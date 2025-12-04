package com.rating.api.controller;

import com.rating.api.entity.MedBatch;
import com.rating.api.service.MedBatchService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/med-batches")
public class MedBatchController {
  private final MedBatchService medBatchService;

  public MedBatchController(MedBatchService medBatchService) {
    this.medBatchService = medBatchService;
  }

  @PostMapping
  public ResponseEntity<?> createMedBatch(@RequestBody MedBatch medBatch) {
    try {
      MedBatch createdBatch = medBatchService.createMedBatch(medBatch);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdBatch);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getBatchById(@PathVariable Long id) {
    return medBatchService
        .getBatchById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/medicine/{medicineId}")
  public ResponseEntity<List<MedBatch>> getBatchesByMedicine(@PathVariable Long medicineId) {
    List<MedBatch> batches = medBatchService.getBatchesByMedicineId(medicineId);
    return ResponseEntity.ok(batches);
  }

  @GetMapping("/expired")
  public ResponseEntity<List<MedBatch>> getExpiredBatches() {
    List<MedBatch> expiredBatches = medBatchService.getExpiredBatches();
    return ResponseEntity.ok(expiredBatches);
  }

  @GetMapping("/expiring")
  public ResponseEntity<List<MedBatch>> getBatchesExpiringBetween(
      @RequestParam String startDate, @RequestParam String endDate) {
    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);
    List<MedBatch> batches = medBatchService.getBatchesExpiringBetween(start, end);
    return ResponseEntity.ok(batches);
  }

  @PatchMapping("/{batchId}/quantity")
  public ResponseEntity<?> updateBatchQuantity(
      @PathVariable Long batchId, @RequestBody Map<String, Long> updateRequest) {
    try {
      Long newQuantity = updateRequest.get("quantity");
      MedBatch updatedBatch = medBatchService.updateBatchQuantity(batchId, newQuantity);
      return ResponseEntity.ok(updatedBatch);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBatch(@PathVariable Long id) {
    try {
      medBatchService.deleteBatch(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
