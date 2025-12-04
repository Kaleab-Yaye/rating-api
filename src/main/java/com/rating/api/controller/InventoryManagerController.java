package com.rating.api.controller;

import com.rating.api.entity.InventoryManager;
import com.rating.api.service.InventoryManagerService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory-managers")
public class InventoryManagerController {
  private final InventoryManagerService inventoryManagerService;

  public InventoryManagerController(InventoryManagerService inventoryManagerService) {
    this.inventoryManagerService = inventoryManagerService;
  }

  @PostMapping
  public ResponseEntity<?> createInventoryManager(@RequestBody InventoryManager inventoryManager) {
    try {
      InventoryManager createdManager =
          inventoryManagerService.createInventoryManager(inventoryManager);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdManager);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping
  public ResponseEntity<List<InventoryManager>> getAllInventoryManagers() {
    List<InventoryManager> managers = inventoryManagerService.getAllInventoryManagers();
    return ResponseEntity.ok(managers);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getInventoryManagerById(@PathVariable UUID id) {
    return inventoryManagerService
        .getInventoryManagerById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<?> getInventoryManagerByEmail(@PathVariable String email) {
    return inventoryManagerService
        .getInventoryManagerByEmail(email)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateInventoryManager(
      @PathVariable UUID id, @RequestBody InventoryManager managerDetails) {
    try {
      InventoryManager updatedManager =
          inventoryManagerService.updateInventoryManager(id, managerDetails);
      return ResponseEntity.ok(updatedManager);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteInventoryManager(@PathVariable UUID id) {
    try {
      inventoryManagerService.deleteInventoryManager(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}/is-admin")
  public ResponseEntity<Map<String, Boolean>> isAdmin(@PathVariable UUID id) {
    boolean isAdmin = inventoryManagerService.isAdmin(id);
    return ResponseEntity.ok(Map.of("isAdmin", isAdmin));
  }
}
