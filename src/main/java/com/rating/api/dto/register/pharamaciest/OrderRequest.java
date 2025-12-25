package com.rating.api.dto.register.pharamaciest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record OrderRequest(
    @NotBlank Map<Long, Integer> orderListFromPharmacist, @NotNull Long pharmacyID) {}
