package com.rating.api.repository;

import com.rating.api.domain.InventoryManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryMangerRepo extends JpaRepository<InventoryManager, UUID> {
    Optional<InventoryManager> getInventoryManagerByEmail(String email);
}
