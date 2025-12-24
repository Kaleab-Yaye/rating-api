package com.rating.api.service.users.pharamaserv;

import com.rating.api.domain.Pharmacist;
import com.rating.api.dto.register.RegisterPharmacistRequest;
import com.rating.api.dto.register.admindto.pharmacist.AddPharmacistToPharmacyRequest;
import com.rating.api.repository.PharmacistRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PharmacistService {
  private final PasswordEncoder passwordEncoder;
  private final PharmacistRepo pharmacistRepo;

  PharmacistService(PasswordEncoder passwordEncoder, PharmacistRepo pharmacistRepo) {
    this.passwordEncoder = passwordEncoder;
    this.pharmacistRepo = pharmacistRepo;
  }

  public void register(RegisterPharmacistRequest registerPharmacistRequest) {
    Pharmacist pharmacist = new Pharmacist();
    pharmacist.setName(registerPharmacistRequest.name());
    pharmacist.setEmail(registerPharmacistRequest.email());
    pharmacist.setIsAdmin(registerPharmacistRequest.isAdmin());
    pharmacist.setPharmacy(registerPharmacistRequest.pharmacy());
    pharmacist.setType(registerPharmacistRequest.type());
    pharmacist.setPassword(
        passwordEncoder.encode(
            registerPharmacistRequest.password())); // here is where the password is getting hashed

    pharmacistRepo.save(pharmacist);
  }

  public void addPharmacistToPharmacy(
      AddPharmacistToPharmacyRequest addPharmacistToPharmacyRequest) {
    Pharmacist pharmacist =
        pharmacistRepo
            .getPharmacistsByEmail(addPharmacistToPharmacyRequest.email())
            .orElseThrow(); // first extract the pharmcist
    pharmacist.setPharmacy(
        pharmacistRepo
            .getPharmacistsById(addPharmacistToPharmacyRequest.pharmacyId())
            .get()
            .getPharmacy()); // let hibrnate handel the table joining & we know the pharcy exits so
    // no need for check logic here
  }
}
