package com.rating.api.dto;

import com.rating.api.domain.Pharmacy;
import jakarta.validation.constraints.*;

public record RegisterPharmacistRequest(
        @NotBlank(message = "name should be valid") @Size(message = "the name is too short", min = 3, max = 32) String name,
        @NotBlank(message = "Email is needed") @Email(message = "not formal email addresss") String email,
        Pharmacy pharmacy,
        @NotNull(message = "admin stat is needed") Boolean isAdmin,
        @NotBlank(message = "passsword is needed") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])\\S{8,20}$") // minum
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
        String type) {
}
