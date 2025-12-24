package com.rating.api.web.controller.users.inventmangs;

import com.rating.api.dto.register.admindto.inventmngs.AddInvntMangRequest;
import com.rating.api.service.users.Invetmangserv.InventoryMangsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/invent/add_invent_mangs")
public class AddInventMang {
  @Autowired InventoryMangsService inventoryMangsService;

  @PreAuthorize("hasRole('INVENTORYMANAGER') and hasRole('ADMIN') and hasRole('APPROVED')")
  @PostMapping
  public ResponseEntity<String> addInventMang(
      @Validated @RequestBody AddInvntMangRequest addInvntMang) {
    inventoryMangsService.addInventMang(addInvntMang);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body("InventoryMang with the Email" + addInvntMang.email() + " added successfully");
  }
}
