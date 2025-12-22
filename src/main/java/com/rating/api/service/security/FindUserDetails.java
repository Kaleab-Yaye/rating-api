package com.rating.api.service.security;

import com.rating.api.domain.InventoryManager;
import com.rating.api.domain.Pharmacist;
import com.rating.api.repository.InventoryMangerRepo;
import com.rating.api.repository.PharmacistRepo;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.security.core.userdetails.User;
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
      // to collect the roles in a do pattern way
      ArrayList<String> role = new ArrayList<>();
      role.add(pharmacist.getType());
      if (pharmacist.getIsAdmin()) {
        role.add("ADMIN");
      }

      return User.builder()
          .username(pharmacist.getId().toString()) // has to be email or ID for futre use
          .password(pharmacist.getPassword())
          .roles(role.toArray(role.toArray(new String[0]))) // change the role in to an array frist
          .build();

    } else {
      InventoryManager inventoryManager =
          inventoryMangerRepo
              .getInventoryManagerByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException("No user with the Email: " + email));
      return org.springframework.security.core.userdetails.User.builder()
          .username(inventoryManager.getId().toString())
          .password(inventoryManager.getPassword())
          .roles(inventoryManager.getType() + inventoryManager.getIsAdmin().toString())
          .build();
    }
  }
}
