package io.github.pedrozaz.orderservice.domain;

public enum OrderStatus {
    PENDING_PAYMENT,
    PAID,
    CANCELLED_PAYMENT_FAILED,
    SHIPPED
}
