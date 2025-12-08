package com.rating.api.service.security;

import com.rating.api.domain.InventoryManager;
import com.rating.api.domain.Pharmacist;
import com.rating.api.repository.InventoryMangerRepo;
import com.rating.api.repository.PharmacistRepo;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FindUserDetails implements UserDetailsService {
  private final PharmacistRepo pharmacistRepo;
  private final InventoryMangerRepo inventoryMangerRepo;

  FindUserDetails(PharmacistRepo pharmacistRepo, InventoryMangerRepo inventoryMangerRepo) {
    this.pharmacistRepo = pharmacistRepo;
    this.inventoryMangerRepo = inventoryMangerRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Pharmacist> optionalPharmacistpharmacist = pharmacistRepo.getPharmacistsByEmail(email);
    if (optionalPharmacistpharmacist.isPresent()) {
      Pharmacist pharmacist = optionalPharmacistpharmacist.get();
      return org.springframework.security.core.userdetails.User.builder()
          .username(pharmacist.getName())
          .password(pharmacist.getPassword())
          .roles(pharmacist.getType() + pharmacist.getIsAdmin().toString())
          .build();

    } else {
      InventoryManager inventoryManager =
          inventoryMangerRepo
              .getInventoryManagerByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException("No user with the Email: " + email));
      return org.springframework.security.core.userdetails.User.builder()
          .username(inventoryManager.getName())
          .password(inventoryManager.getPassword())
          .roles(inventoryManager.getType() + inventoryManager.getIsAdmin().toString())
          .build();
    }
  }
}
