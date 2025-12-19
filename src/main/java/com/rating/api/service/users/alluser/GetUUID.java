package com.rating.api.service.users.alluser;

import com.rating.api.domain.InventoryManager;
import com.rating.api.domain.Pharmacist;
import com.rating.api.repository.InventoryMangerRepo;
import com.rating.api.repository.PharmacistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUUID {
    @Autowired
    private InventoryMangerRepo inventoryMangerRepo;
    @Autowired
    PharmacistRepo pharmacistRepo;

    GetUUID(InventoryMangerRepo inventoryMangerRepo, PharmacistRepo pharmacistRepo) {
        this.inventoryMangerRepo = inventoryMangerRepo;
        this.pharmacistRepo = pharmacistRepo;
    }

    public UUID  getUUID(String email) {
        Optional <Pharmacist> pharmacist= pharmacistRepo.getPharmacistsByEmail(email);
        Optional< InventoryManager> inventoryManager = inventoryMangerRepo.getInventoryManagerByEmail(email);
        if (pharmacist.isPresent()) {
            return pharmacist.get().getId();
        }
        else {
            return inventoryManager.get().getId();
        }
    }



}
