package com.rating.api.web.controller.users.inventmangs;

import com.rating.api.dto.register.RegisterInventoryMangRequest;
import com.rating.api.service.users.Invetmangserv.InventoryMangsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register/InventManeg")
public class RegisterNewInventMang {
  @Autowired private final InventoryMangsService inventoryMangsService;

  RegisterNewInventMang(InventoryMangsService inventoryMangsService) {
    this.inventoryMangsService = inventoryMangsService;
  }

  @PostMapping
  public ResponseEntity<String> registerNewInventMang(
      @RequestBody RegisterInventoryMangRequest registerInventoryMangRequest) {
    inventoryMangsService.register(registerInventoryMangRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            "User with the name "
                + registerInventoryMangRequest.name()
                + " is succsufully registerd");
  }
}
