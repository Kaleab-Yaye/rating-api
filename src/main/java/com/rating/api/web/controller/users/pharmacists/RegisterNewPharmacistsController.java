package com.rating.api.web.controller.users.pharmacists;

import com.rating.api.dto.register.RegisterPharmacistRequest;
import com.rating.api.service.users.pharamaserv.PharmacistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register/pharmacists")
public class RegisterNewPharmacistsController {

  private final PharmacistService pharmacistService;

  RegisterNewPharmacistsController(PharmacistService pharmacistService) {
    this.pharmacistService = pharmacistService;
  }

  @PostMapping
  public ResponseEntity<String> register(@Valid @RequestBody RegisterPharmacistRequest request) {
    pharmacistService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED).body("user " + request.name() + " created");
  }
}
