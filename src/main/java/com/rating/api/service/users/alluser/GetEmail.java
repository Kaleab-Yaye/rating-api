package com.rating.api.service.users.alluser;

import com.rating.api.domain.InventoryManager;
import com.rating.api.domain.Pharmacist;
import com.rating.api.repository.InventoryMangerRepo;
import com.rating.api.repository.PharmacistRepo;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetEmail {
  @Autowired private InventoryMangerRepo inventoryMangerRepo;
  @Autowired PharmacistRepo pharmacistRepo;

  GetEmail(InventoryMangerRepo inventoryMangerRepo, PharmacistRepo pharmacistRepo) {
    this.inventoryMangerRepo = inventoryMangerRepo;
    this.pharmacistRepo = pharmacistRepo;
  }

  public String getEmail(UUID uuid) {
    Optional<Pharmacist> pharmacist = pharmacistRepo.getPharmacistsById(uuid);
    Optional<InventoryManager> inventoryManager = inventoryMangerRepo.getInventoryManagerById(uuid);
    if (pharmacist.isPresent()) {
      return pharmacist.get().getEmail();
    } else {
      return inventoryManager.get().getEmail();
    }
  }
}
