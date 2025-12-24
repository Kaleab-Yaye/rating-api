package com.rating.api.web.controller.users.pharmacists;

import com.rating.api.dto.register.admindto.pharmacist.AddPharmacyRequest;
import com.rating.api.service.other.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pharmacist")
public class AddPharmacy {
  @Autowired PharmacyService pharmacyService;

  @PostMapping("/create/pharmacy")
  public ResponseEntity<String> addPharmacy(AddPharmacyRequest addPharmacyRequest) {
    pharmacyService.addPharmacy(addPharmacyRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body("Pharmacy" + addPharmacyRequest.name() + " added successfully");
  }
}
