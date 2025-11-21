package io.github.pedrozaz.orderservice.infra.integration.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        UUID payerId,
        UUID payeeId,
        BigDecimal amount
) {
}
