package com.rating.api.repository;

import com.rating.api.domain.InventoryManager;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryMangerRepo extends JpaRepository<InventoryManager, UUID> {
  Optional<InventoryManager> getInventoryManagerByEmail(String email);
}
