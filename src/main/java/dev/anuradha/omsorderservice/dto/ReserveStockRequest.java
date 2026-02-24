package dev.anuradha.omsorderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ReserveStockRequest {

    private UUID productId;
    private Integer quantity;
}
