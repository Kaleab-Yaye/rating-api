package com.rating.api.controller;

import com.rating.api.entity.Pharmacist;
import com.rating.api.service.PharmacistService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pharmacists")
public class PharmacistController {
  private final PharmacistService pharmacistService;

  public PharmacistController(PharmacistService pharmacistService) {
    this.pharmacistService = pharmacistService;
  }

  @PostMapping
  public ResponseEntity<?> createPharmacist(@RequestBody Pharmacist pharmacist) {
    try {
      Pharmacist createdPharmacist = pharmacistService.createPharmacist(pharmacist);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdPharmacist);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping
  public ResponseEntity<List<Pharmacist>> getAllPharmacists() {
    List<Pharmacist> pharmacists = pharmacistService.getAllPharmacists();
    return ResponseEntity.ok(pharmacists);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPharmacistById(@PathVariable UUID id) {
    return pharmacistService
        .getPharmacistById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<?> getPharmacistByEmail(@PathVariable String email) {
    return pharmacistService
        .getPharmacistByEmail(email)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/pharmacy/{pharmacyId}")
  public ResponseEntity<List<Pharmacist>> getPharmacistsByPharmacy(@PathVariable Long pharmacyId) {
    List<Pharmacist> pharmacists = pharmacistService.getPharmacistsByPharmacyId(pharmacyId);
    return ResponseEntity.ok(pharmacists);
  }

  @GetMapping("/pharmacy/{pharmacyId}/admins")
  public ResponseEntity<List<Pharmacist>> getAdminPharmacistsByPharmacy(
      @PathVariable Long pharmacyId) {
    List<Pharmacist> pharmacists = pharmacistService.getAdminPharmacistsByPharmacyId(pharmacyId);
    return ResponseEntity.ok(pharmacists);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePharmacist(
      @PathVariable UUID id, @RequestBody Pharmacist pharmacistDetails) {
    try {
      Pharmacist updatedPharmacist = pharmacistService.updatePharmacist(id, pharmacistDetails);
      return ResponseEntity.ok(updatedPharmacist);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePharmacist(@PathVariable UUID id) {
    try {
      pharmacistService.deletePharmacist(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}/is-admin")
  public ResponseEntity<Map<String, Boolean>> isAdmin(@PathVariable UUID id) {
    boolean isAdmin = pharmacistService.isAdmin(id);
    return ResponseEntity.ok(Map.of("isAdmin", isAdmin));
  }
}
