package com.rating.api.web.controller.users.inventmangs;

import com.rating.api.dto.register.Inventmng.AddMedBatchRequest;
import com.rating.api.dto.register.Inventmng.AddMedicineRequest;
import com.rating.api.service.users.Invetmangserv.InventoryMangsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invent")
public class AddNewMedicineAndBatch {
  @Autowired private InventoryMangsService inventoryMangsService;

  @PostMapping("/add_new_med")
  @PostAuthorize("hasRole('INVENTORYMANAGER') and hasRole('APPROVED')")
  public ResponseEntity<String> addMedicine(
      @RequestBody @Validated AddMedicineRequest addMedicineRequest) {
    inventoryMangsService.addMedicine(addMedicineRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body("Created med with the name" + addMedicineRequest.name());
  }

  @PostMapping("/add_new_batch")
  @PostAuthorize("hasRole('INVENTORYMANAGER') and hasRole('APPROVED')")
  public ResponseEntity<String> addNewBatch(
      @RequestBody @Validated AddMedBatchRequest addMedBatchRequest) {
    inventoryMangsService.addNewMedBatch(addMedBatchRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body("Created med batch successfully");
  }
}
