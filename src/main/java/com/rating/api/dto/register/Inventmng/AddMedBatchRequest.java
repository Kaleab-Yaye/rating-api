package com.rating.api.dto.register.Inventmng;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record AddMedBatchRequest(
    @NotBlank String name,
    @NotNull(message = "amount is needed") @Positive Long amountToBeAdded,
    @NotNull @Future(message = "expiary should be in the futre") LocalDate localDate) {}
