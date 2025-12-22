package com.rating.api.dto.register.admindto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddPharmacyRequest(
    @NotBlank(message = "Pharmacy name is required")
        @Size(min = 3, max = 100, message = "Pharmacy name must be between 3 and 100 characters")
        String name,
    @NotBlank(message = "Street address is required") String streetAddress,
    @NotBlank(message = "City is required") String city,
    @NotBlank(message = "Region is required") String region,
    String postalCode) {}
