package com.rating.api.controller;

import com.rating.api.entity.Pharmacy;
import com.rating.api.service.PharmacyService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pharmacies")
public class PharmacyController {
  private final PharmacyService pharmacyService;

  public PharmacyController(PharmacyService pharmacyService) {
    this.pharmacyService = pharmacyService;
  }

  @PostMapping
  public ResponseEntity<?> createPharmacy(@Valid @RequestBody Pharmacy pharmacy) {
    try {
      Pharmacy createdPharmacy = pharmacyService.createPharmacy(pharmacy);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdPharmacy);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping
  public ResponseEntity<List<Pharmacy>> getAllPharmacies() {
    List<Pharmacy> pharmacies = pharmacyService.getAllPharmacies();
    return ResponseEntity.ok(pharmacies);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPharmacyById(@PathVariable Long id) {
    return pharmacyService
        .getPharmacyById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/city/{city}")
  public ResponseEntity<List<Pharmacy>> getPharmaciesByCity(@PathVariable String city) {
    List<Pharmacy> pharmacies = pharmacyService.getPharmaciesByCity(city);
    return ResponseEntity.ok(pharmacies);
  }

  @GetMapping("/region/{region}")
  public ResponseEntity<List<Pharmacy>> getPharmaciesByRegion(@PathVariable String region) {
    List<Pharmacy> pharmacies = pharmacyService.getPharmaciesByRegion(region);
    return ResponseEntity.ok(pharmacies);
  }

  @GetMapping("/low-balance")
  public ResponseEntity<List<Pharmacy>> getPharmaciesWithLowBalance(
      @RequestParam(defaultValue = "50") Long minBalance) {
    List<Pharmacy> pharmacies = pharmacyService.getPharmaciesWithLowBalance(minBalance);
    return ResponseEntity.ok(pharmacies);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePharmacy(
      @PathVariable Long id, @Valid @RequestBody Pharmacy pharmacyDetails) {
    try {
      Pharmacy updatedPharmacy = pharmacyService.updatePharmacy(id, pharmacyDetails);
      return ResponseEntity.ok(updatedPharmacy);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PatchMapping("/{id}/balance")
  public ResponseEntity<?> updatePharmacyBalance(
      @PathVariable Long id, @RequestBody Map<String, Long> balanceUpdate) {
    try {
      Long newBalance = balanceUpdate.get("balance");
      if (newBalance == null) {
        return ResponseEntity.badRequest().body(Map.of("error", "Balance field is required"));
      }
      Pharmacy updatedPharmacy = pharmacyService.updatePharmacyBalance(id, newBalance);
      return ResponseEntity.ok(updatedPharmacy);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePharmacy(@PathVariable Long id) {
    try {
      pharmacyService.deletePharmacy(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
