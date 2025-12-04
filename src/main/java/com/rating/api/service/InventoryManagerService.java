package com.rating.api.service;

import com.rating.api.entity.InventoryManager;
import com.rating.api.repository.InventoryManagerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InventoryManagerService {
  private final InventoryManagerRepository inventoryManagerRepository;

  public InventoryManagerService(InventoryManagerRepository inventoryManagerRepository) {
    this.inventoryManagerRepository = inventoryManagerRepository;
  }

  public InventoryManager createInventoryManager(InventoryManager inventoryManager) {
    if (inventoryManagerRepository.findByEmail(inventoryManager.getEmail()).isPresent()) {
      throw new RuntimeException(
          "Inventory manager with email " + inventoryManager.getEmail() + " already exists");
    }
    return inventoryManagerRepository.save(inventoryManager);
  }

  public Optional<InventoryManager> getInventoryManagerById(UUID id) {
    return inventoryManagerRepository.findById(id);
  }

  public Optional<InventoryManager> getInventoryManagerByEmail(String email) {
    return inventoryManagerRepository.findByEmail(email);
  }

  public List<InventoryManager> getAllInventoryManagers() {
    return inventoryManagerRepository.findAll();
  }

  public InventoryManager updateInventoryManager(UUID id, InventoryManager managerDetails) {
    InventoryManager manager =
        inventoryManagerRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Inventory manager not found with id: " + id));
    manager.setName(managerDetails.getName());
    manager.setEmail(managerDetails.getEmail());
    manager.setIsAdmin(managerDetails.getIsAdmin());
    return inventoryManagerRepository.save(manager);
  }

  public void deleteInventoryManager(UUID id) {
    InventoryManager manager =
        inventoryManagerRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Inventory manager not found with id: " + id));
    inventoryManagerRepository.delete(manager);
  }

  public boolean isAdmin(UUID id) {
    return inventoryManagerRepository.findById(id).map(InventoryManager::getIsAdmin).orElse(false);
  }
}
