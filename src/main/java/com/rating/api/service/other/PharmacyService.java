package com.rating.api.service.other;

import com.rating.api.domain.Pharmacy;
import com.rating.api.dto.register.admindto.AddPharmacyRequest;
import com.rating.api.repository.PharmacyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class PharmacyService {
  @Autowired PharmacyRepo pharmacyRepo;

  public void addPharmacy(AddPharmacyRequest addPharmacyRequest) {
    Pharmacy pharmacy = new Pharmacy();
    pharmacy.setName(addPharmacyRequest.name());
    pharmacy.setRegion(addPharmacyRequest.region());
    pharmacy.setCity(addPharmacyRequest.city());
    pharmacy.setPostalCode(addPharmacyRequest.postalCode());
    pharmacyRepo.save(pharmacy);
  }
}
