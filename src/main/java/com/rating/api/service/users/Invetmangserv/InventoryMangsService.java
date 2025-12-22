package com.rating.api.service.users.Invetmangserv;

import com.rating.api.domain.InventoryManager;
import com.rating.api.dto.register.RegisterInventoryMangRequest;
import com.rating.api.repository.InventoryMangerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InventoryMangsService {
  @Autowired private final InventoryMangerRepo inventoryMangerRepo;
  @Autowired private final PasswordEncoder passwordEncoder;

  InventoryMangsService(InventoryMangerRepo inventoryMangerRepo, PasswordEncoder passwordEncoder) {
    this.inventoryMangerRepo = inventoryMangerRepo;
    this.passwordEncoder = passwordEncoder;
  }

  public void register(RegisterInventoryMangRequest registerInventoryMang) {
    InventoryManager inventoryManager = new InventoryManager();
    inventoryManager.setPassword(passwordEncoder.encode(registerInventoryMang.password()));
    inventoryManager.setEmail(registerInventoryMang.email());
    inventoryManager.setName(registerInventoryMang.name());
    inventoryMangerRepo.save(inventoryManager);
  }
}
