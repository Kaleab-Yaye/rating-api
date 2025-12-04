package com.rating.api.controller;

import com.rating.api.entity.Medicine;
import com.rating.api.service.MedicineService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {
  private final MedicineService medicineService;

  public MedicineController(MedicineService medicineService) {
    this.medicineService = medicineService;
  }

  @PostMapping
  public ResponseEntity<?> createMedicine(@RequestBody Medicine medicine) {
    try {
      Medicine createdMedicine = medicineService.createMedicine(medicine);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdMedicine);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping
  public ResponseEntity<List<Medicine>> getAllMedicines() {
    List<Medicine> medicines = medicineService.getAllMedicines();
    return ResponseEntity.ok(medicines);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getMedicineById(@PathVariable Long id) {
    return medicineService
        .getMedicineById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/search")
  public ResponseEntity<List<Medicine>> searchMedicines(@RequestParam String query) {
    List<Medicine> medicines = medicineService.searchMedicines(query);
    return ResponseEntity.ok(medicines);
  }

  @GetMapping("/rating/{minRating}")
  public ResponseEntity<List<Medicine>> getMedicinesByMinimumRating(
      @PathVariable Integer minRating) {
    List<Medicine> medicines = medicineService.getMedicinesByMinimumRating(minRating);
    return ResponseEntity.ok(medicines);
  }

  @GetMapping("/sorted/rating")
  public ResponseEntity<List<Medicine>> getMedicinesSortedByRating() {
    List<Medicine> medicines = medicineService.getMedicinesOrderByRating();
    return ResponseEntity.ok(medicines);
  }

  @GetMapping("/{id}/stock")
  public ResponseEntity<?> getMedicineStock(@PathVariable Long id) {
    try {
      Long stock = medicineService.getAvailableStock(id);
      Map<String, Object> response = new HashMap<>();
      response.put("medicineId", id);
      response.put("availableStock", stock);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateMedicine(
      @PathVariable Long id, @RequestBody Medicine medicineDetails) {
    try {
      Medicine updatedMedicine = medicineService.updateMedicine(id, medicineDetails);
      return ResponseEntity.ok(updatedMedicine);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
    try {
      medicineService.deleteMedicine(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
