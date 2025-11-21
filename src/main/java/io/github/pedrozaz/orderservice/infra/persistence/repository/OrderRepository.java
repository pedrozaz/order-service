package io.github.pedrozaz.orderservice.infra.persistence.repository;

import io.github.pedrozaz.orderservice.infra.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository  extends JpaRepository<OrderEntity, UUID> {
}
