package com.rating.api.service;

import com.rating.api.entity.Pharmacy;
import com.rating.api.repository.PharmacyRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PharmacyService {
  private final PharmacyRepository pharmacyRepository;

  public PharmacyService(PharmacyRepository pharmacyRepository) {
    this.pharmacyRepository = pharmacyRepository;
  }

  public Pharmacy createPharmacy(Pharmacy pharmacy) {
    if (pharmacyRepository.findByName(pharmacy.getName()).isPresent()) {
      throw new RuntimeException("Pharmacy with name " + pharmacy.getName() + " already exists");
    }
    return pharmacyRepository.save(pharmacy);
  }

  public Optional<Pharmacy> getPharmacyById(Long id) {
    return pharmacyRepository.findById(id);
  }

  public List<Pharmacy> getAllPharmacies() {
    return pharmacyRepository.findAll();
  }

  public List<Pharmacy> getPharmaciesByCity(String city) {
    return pharmacyRepository.findByCity(city);
  }

  public List<Pharmacy> getPharmaciesByRegion(String region) {
    return pharmacyRepository.findByRegion(region);
  }

  public List<Pharmacy> getPharmaciesWithLowBalance(Long minBalance) {
    return pharmacyRepository.findWithLowBalance(minBalance);
  }

  public Pharmacy updatePharmacy(Long id, Pharmacy pharmacyDetails) {
    Pharmacy pharmacy =
        pharmacyRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Pharmacy not found with id: " + id));
    pharmacy.setName(pharmacyDetails.getName());
    pharmacy.setStreetAddress(pharmacyDetails.getStreetAddress());
    pharmacy.setCity(pharmacyDetails.getCity());
    pharmacy.setRegion(pharmacyDetails.getRegion());
    pharmacy.setPostalCode(pharmacyDetails.getPostalCode());
    return pharmacyRepository.save(pharmacy);
  }

  public Pharmacy updatePharmacyBalance(Long id, Long newBalance) {
    Pharmacy pharmacy =
        pharmacyRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Pharmacy not found with id: " + id));
    pharmacy.setBalance(newBalance);
    return pharmacyRepository.save(pharmacy);
  }

  public void deletePharmacy(Long id) {
    Pharmacy pharmacy =
        pharmacyRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Pharmacy not found with id: " + id));
    pharmacyRepository.delete(pharmacy);
  }
}
