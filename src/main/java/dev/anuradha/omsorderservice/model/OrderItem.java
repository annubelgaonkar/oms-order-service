package dev.anuradha.omsorderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID orderId;

    private UUID productId;

    private Integer quantity;

    private BigDecimal price;
}
