package com.rating.api.dto.register.admindto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddPharmacistToPharmacyRequest(
    @NotBlank(message = "Email is needed") @Email(message = "not formal email addresss")
        String email,
    @NotBlank(message = "ID cannot be empetey") @NotNull Long PharmacyId) {}
