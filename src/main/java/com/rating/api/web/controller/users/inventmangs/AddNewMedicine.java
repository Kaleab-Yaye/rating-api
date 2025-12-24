package com.rating.api.web.controller.users.inventmangs;

import com.rating.api.dto.register.Inventmng.AddMedicineRequest;
import com.rating.api.service.users.Invetmangserv.InventoryMangsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invent/add_new_med")
public class AddNewMedicine {
  @Autowired private InventoryMangsService inventoryMangsService;

  @PostMapping
  @PostAuthorize("hasRole('INVENTORYMANAGER') and hasRole('APPROVED')")
  public ResponseEntity<String> addMedicine(
      @RequestBody @Validated AddMedicineRequest addMedicineRequest) {
    inventoryMangsService.addMedicine(addMedicineRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body("Created med with the name" + addMedicineRequest.name());
  }
}
