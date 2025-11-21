package io.github.pedrozaz.orderservice.infra.controller;

import io.github.pedrozaz.orderservice.application.usecase.CreateOrderUseCase;
import io.github.pedrozaz.orderservice.infra.controller.dto.CreateOrderRequest;
import io.github.pedrozaz.orderservice.infra.persistence.entity.OrderEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderEntity> create(@RequestBody @Valid CreateOrderRequest request) {
        OrderEntity order = createOrderUseCase.createOrder(
                request.customerWalletId(),
                request.storeWalletId(),
                request.amount()
        );

        return ResponseEntity.ok(order);
    }
}
