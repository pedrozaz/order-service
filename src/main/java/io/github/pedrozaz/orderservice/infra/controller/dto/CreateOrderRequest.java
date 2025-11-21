package io.github.pedrozaz.orderservice.infra.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull UUID customerWalletId,
        @NotNull UUID storeWalletId,
        @NotNull @Positive BigDecimal amount
) {
}
