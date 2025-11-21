# Order Service

A simple E-commerce Order Management microservice responsible for handling customer orders and orchestrating payments via synchronous integration.

## Key Features

* **Synchronous Integration:** Uses **Spring Cloud OpenFeign** to communicate seamlessly with the external *Secure Payment Service*. [https://github.com/pedrozaz/secure-payment-service]
* **Idempotency Strategy:** Leverages the Order ID as an `Idempotency-Key` when calling the payment provider, ensuring that a single order is never charged twice, even if network retries occur.
* **Saga Pattern (Simplified):** Implements local state management (`PENDING` -> `PAID` or `CANCELLED`) to handle distributed transaction consistency.
* **Clean Architecture:** Separation of concerns between Domain, Application (Use Cases), and Infrastructure.

##  Tech Stack

* **Java 21**
* **Spring Boot 3.5**
* **Spring Cloud OpenFeign**
* **PostgreSQL**
* **Flyway**
* **Docker**

## Architecture & Integration

This service acts as a client to the Payment Core:

```mermaid
sequenceDiagram
    participant Client
    participant OrderService
    participant OrderDB
    participant PaymentService

    Client->>OrderService: POST /orders
    OrderService->>OrderDB: Save Order (PENDING)
    OrderService->>PaymentService: POST /transfers

    alt Payment Success
        PaymentService-->>OrderService: 200 OK (Transaction ID)
        OrderService->>OrderDB: Update Order (PAID)
        OrderService-->>Client: Order Confirmed (200 OK)
    else Payment Failed
        PaymentService-->>OrderService: Error (422/500)
        OrderService->>OrderDB: Update Order (CANCELLED)
        OrderService-->>Client: Order Cancelled (200 OK)
    end
  ```

## API Reference
### Create Order

Endpoint: 
> POST /api/orders

Body:

  ```JSON
{
  "customerWalletId": "uuid-customer",
  "storeWalletId": "uuid-store",
  "amount": 100.00
}
  ```
---