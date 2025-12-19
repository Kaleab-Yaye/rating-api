package com.rating.api.dto.register;

import jakarta.validation.constraints.*;

public record RegisterInventoryMangRequest(
    @NotBlank(message = "Name cannot be empetey")
        @Size(message = "the name is too short or too long", min = 3, max = 32)
        String name,
    @NotBlank(message = "Email is needed") @Email(message = "not formal email addresss")
        String email,
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])\\S{8,20}$") // minum
        // of
        // lenth
        // 8
        // passowrd
        // and
        // includes
        // the
        // combiantion
        // of
        // charchters
        // of
        // our
        // need
        String password,
    @NotNull(message = "admin stat is needed") Boolean isAdmin) {}
