package dev.anuradha.omsorderservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreatedEvent {

    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;


}
