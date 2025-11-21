package io.github.pedrozaz.orderservice.application.usecase;

import feign.FeignException;
import io.github.pedrozaz.orderservice.domain.OrderStatus;
import io.github.pedrozaz.orderservice.infra.integration.PaymentClient;
import io.github.pedrozaz.orderservice.infra.integration.dto.PaymentRequest;
import io.github.pedrozaz.orderservice.infra.integration.dto.PaymentResponse;
import io.github.pedrozaz.orderservice.infra.persistence.entity.OrderEntity;
import io.github.pedrozaz.orderservice.infra.persistence.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;

    public CreateOrderUseCase(OrderRepository orderRepository, PaymentClient paymentClient) {
        this.orderRepository = orderRepository;
        this.paymentClient = paymentClient;
    }

    public OrderEntity createOrder(UUID customerWalletId, UUID storeWalletId, BigDecimal amount) {
        OrderEntity order = new OrderEntity();
        order.setCustomerWalletId(customerWalletId);
        order.setTotalAmount(amount);
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setCreatedAt(LocalDateTime.now());

        OrderEntity savedOrder = orderRepository.save(order);

        try {
            PaymentRequest request = new PaymentRequest(
                    customerWalletId,
                    storeWalletId,
                    amount
            );

            PaymentResponse paymentResponse = paymentClient.processPayment(
                    request,
                    savedOrder.getId().toString()
            );

            savedOrder.setStatus(OrderStatus.PAID);
            savedOrder.setPaymentTransactionId(paymentResponse.transactionId().toString());
        } catch (FeignException.UnprocessableEntity e) {
            System.err.println("Payment refused: " + e.contentUTF8());
            savedOrder.setStatus(OrderStatus.CANCELLED_PAYMENT_FAILED);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            savedOrder.setStatus(OrderStatus.CANCELLED_PAYMENT_FAILED);
        }

        return orderRepository.save(savedOrder);
    }
}
