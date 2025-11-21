package io.github.pedrozaz.orderservice.infra.integration;

import io.github.pedrozaz.orderservice.infra.integration.dto.PaymentRequest;
import io.github.pedrozaz.orderservice.infra.integration.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "payment-gateway", url = "${integration.payment-service.url}")
public interface PaymentClient {

    @PostMapping("/api/transfers")
    PaymentResponse processPayment(
            @RequestBody PaymentRequest request,
            @RequestHeader("Idempotency-Key")  String idempotencyKey
    );
}
