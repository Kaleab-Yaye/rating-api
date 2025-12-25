package com.rating.api.service.users.Invetmangserv;

import com.rating.api.domain.InventoryManager;
import com.rating.api.domain.MedBatch;
import com.rating.api.domain.Medicine;
import com.rating.api.dto.register.Inventmng.AddMedBatchRequest;
import com.rating.api.dto.register.Inventmng.AddMedicineRequest;
import com.rating.api.dto.register.RegisterInventoryMangRequest;
import com.rating.api.dto.register.admindto.inventmngs.AddInvntMangRequest;
import com.rating.api.repository.InventoryMangerRepo;
import com.rating.api.repository.MedBatchRepo;
import com.rating.api.repository.MedicineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InventoryMangsService {
  @Autowired private final InventoryMangerRepo inventoryMangerRepo;
  @Autowired private final PasswordEncoder passwordEncoder;
  @Autowired private final MedicineRepo medicineRepo;
  @Autowired private final MedBatchRepo medBatchRepo;

  InventoryMangsService(
      InventoryMangerRepo inventoryMangerRepo,
      PasswordEncoder passwordEncoder,
      MedicineRepo medicineRepo,
      MedBatchRepo medBatchRepo) {
    this.inventoryMangerRepo = inventoryMangerRepo;
    this.passwordEncoder = passwordEncoder;
    this.medicineRepo = medicineRepo;
    this.medBatchRepo = medBatchRepo;
  }

  public void register(RegisterInventoryMangRequest registerInventoryMang) {
    InventoryManager inventoryManager = new InventoryManager();
    inventoryManager.setPassword(passwordEncoder.encode(registerInventoryMang.password()));
    inventoryManager.setEmail(registerInventoryMang.email());
    inventoryManager.setName(registerInventoryMang.name());
    inventoryManager.setIsAdmin(registerInventoryMang.isAdmin());
    inventoryMangerRepo.save(inventoryManager);
  }

  public void addInventMang(AddInvntMangRequest addInvntMang) {
    InventoryManager inventoryManager =
        inventoryMangerRepo.getInventoryManagerByEmail(addInvntMang.email()).orElseThrow();
    inventoryManager.setApproved(true);
    inventoryMangerRepo.save(inventoryManager);
  }

  public void addMedicine(AddMedicineRequest addMedicineRequest) {
    Medicine medicine = new Medicine();
    medicine.setName(addMedicineRequest.name());
    medicine.setPrice(addMedicineRequest.price());
    medicine.setAbout(addMedicineRequest.about());

    medicineRepo.save(medicine);
  }

  public void addNewMedBatch(AddMedBatchRequest addMedBatchRequest) {
    MedBatch medBatch = new MedBatch();
    Medicine medicine = medicineRepo.findMedicineByName(addMedBatchRequest.name()).orElseThrow();
    medBatch.setMedicine(medicine);
    medBatch.setAmountPresent(addMedBatchRequest.amountToBeAdded());
    medBatch.setExpiry(addMedBatchRequest.localDate());
    medBatchRepo.save(medBatch);
  }
}
