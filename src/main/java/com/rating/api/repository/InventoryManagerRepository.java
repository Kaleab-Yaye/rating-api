package com.rating.api.repository;

import com.rating.api.entity.InventoryManager;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryManagerRepository extends JpaRepository<InventoryManager, UUID> {
  Optional<InventoryManager> findByEmail(String email);

  Optional<InventoryManager> findByName(String name);

  boolean existsByEmail(String email);
}
