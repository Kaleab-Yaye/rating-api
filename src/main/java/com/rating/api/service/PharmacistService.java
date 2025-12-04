package com.rating.api.service;

import com.rating.api.entity.Pharmacist;
import com.rating.api.entity.Pharmacy;
import com.rating.api.repository.PharmacistRepository;
import com.rating.api.repository.PharmacyRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PharmacistService {
  private final PharmacistRepository pharmacistRepository;
  private final PharmacyRepository pharmacyRepository;

  public PharmacistService(
      PharmacistRepository pharmacistRepository, PharmacyRepository pharmacyRepository) {
    this.pharmacistRepository = pharmacistRepository;
    this.pharmacyRepository = pharmacyRepository;
  }

  public Pharmacist createPharmacist(Pharmacist pharmacist) {
    if (pharmacistRepository.findByEmail(pharmacist.getEmail()).isPresent()) {
      throw new RuntimeException(
          "Pharmacist with email " + pharmacist.getEmail() + " already exists");
    }
    Pharmacy pharmacy =
        pharmacyRepository
            .findById(pharmacist.getPharmacy().getId())
            .orElseThrow(() -> new RuntimeException("Pharmacy not found"));
    pharmacist.setPharmacy(pharmacy);
    return pharmacistRepository.save(pharmacist);
  }

  public Optional<Pharmacist> getPharmacistById(UUID id) {
    return pharmacistRepository.findById(id);
  }

  public Optional<Pharmacist> getPharmacistByEmail(String email) {
    return pharmacistRepository.findByEmail(email);
  }

  public List<Pharmacist> getPharmacistsByPharmacyId(Long pharmacyId) {
    return pharmacistRepository.findByPharmacyId(pharmacyId);
  }

  public List<Pharmacist> getAdminPharmacistsByPharmacyId(Long pharmacyId) {
    return pharmacistRepository.findAdminsByPharmacyId(pharmacyId);
  }

  public List<Pharmacist> getAllPharmacists() {
    return pharmacistRepository.findAll();
  }

  public Pharmacist updatePharmacist(UUID id, Pharmacist pharmacistDetails) {
    Pharmacist pharmacist =
        pharmacistRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Pharmacist not found with id: " + id));
    pharmacist.setName(pharmacistDetails.getName());
    pharmacist.setEmail(pharmacistDetails.getEmail());
    pharmacist.setIsAdmin(pharmacistDetails.getIsAdmin());
    return pharmacistRepository.save(pharmacist);
  }

  public void deletePharmacist(UUID id) {
    Pharmacist pharmacist =
        pharmacistRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Pharmacist not found with id: " + id));
    pharmacistRepository.delete(pharmacist);
  }

  public boolean isAdmin(UUID id) {
    return pharmacistRepository.findById(id).map(Pharmacist::getIsAdmin).orElse(false);
  }
}
