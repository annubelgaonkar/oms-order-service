package dev.anuradha.omsorderservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class CreateOrderRequest {

    @NotNull
    private UUID userId;

    @NotEmpty
    private List<OrderItemRequest> items;
}
