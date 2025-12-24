package com.rating.api.dto.register.admindto.inventmngs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AddInvntMangRequest(
    @NotBlank(message = "Email is needed") @Email(message = "not formal email addresss")
        String email) {}
