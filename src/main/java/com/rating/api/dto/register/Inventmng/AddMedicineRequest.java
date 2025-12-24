package com.rating.api.dto.register.Inventmng;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AddMedicineRequest(
    @NotBlank(message = "name cannot be empty") String name, String about, @Positive Long price) {}
