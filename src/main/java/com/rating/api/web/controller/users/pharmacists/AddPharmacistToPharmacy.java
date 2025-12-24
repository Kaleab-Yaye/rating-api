package com.rating.api.web.controller.users.pharmacists;

import com.rating.api.dto.register.admindto.pharmacist.AddPharmacistToPharmacyRequest;
import com.rating.api.service.users.pharamaserv.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pharmacist")
public class AddPharmacistToPharmacy {
  @Autowired PharmacistService pharmacistService;

  @PostMapping("/add_new_pharmacist_to_pharmacy")
  public ResponseEntity<String> addPharmacistToPharmacy(
      @RequestBody AddPharmacistToPharmacyRequest addPharmacistToPharmacyRequest) {
    pharmacistService.addPharmacistToPharmacy(addPharmacistToPharmacyRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body("Pharmacist added successfully");
  }
}
