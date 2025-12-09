package com.rating.api.dto;

import com.rating.api.domain.Pharmacy;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterPharmacistRequest(
        @NotBlank(message = "name should be valid")
        @Size(message = "the name is too short", min = 3, max = 32)
        String name,
        @NotBlank(message = "Email is needed")
        @Email(message = "not formal email addresss")
        String email,
        Pharmacy pharmacy,
        @NotBlank(message = "admin stat is needed")
        Boolean isAdmin,
        @NotBlank(message = "passsword is needed")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\\\S+$).{8,20}$")// minum of lenth 8 passowrd and includes the combiantion of charchters of our need
        String password,
        String type) {
}
