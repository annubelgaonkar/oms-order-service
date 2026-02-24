package dev.anuradha.omsorderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class ProductResponse {

    private UUID id;
    private String name;
    private BigDecimal price;
    private Boolean active;
}
