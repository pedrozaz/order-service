package io.github.pedrozaz.orderservice.infra.integration.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(
        UUID transactionId,
        String status,
        BigDecimal amount,
        String timestamp
) {
}
