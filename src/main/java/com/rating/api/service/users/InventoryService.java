package com.rating.api.service.users;

import com.rating.api.domain.InventoryManager;
import com.rating.api.dto.register.RegisterInventoryMang;
import com.rating.api.repository.InventoryMangerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
   @Autowired
    private final InventoryMangerRepo inventoryMangerRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    InventoryService(InventoryMangerRepo inventoryMangerRepo, PasswordEncoder passwordEncoder) {
        this.inventoryMangerRepo = inventoryMangerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterInventoryMang registerInventoryMang) {
        InventoryManager inventoryManager = new InventoryManager();
        inventoryManager.setPassword(passwordEncoder.encode(registerInventoryMang.password()));
        inventoryManager.setEmail(registerInventoryMang.email());
        inventoryManager.setName(registerInventoryMang.name());
        inventoryMangerRepo.save(inventoryManager);
    }


}
