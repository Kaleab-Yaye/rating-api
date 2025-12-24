package com.rating.api.dto.register.admindto.pharmacist;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record AddPharmacistToPharmacyRequest(
    @NotBlank(message = "Email is needed") @Email(message = "not formal email addresss")
        String email,
    @NotNull @UUID(message = "Not a correct UUID format") java.util.UUID pharmacyId) {}
