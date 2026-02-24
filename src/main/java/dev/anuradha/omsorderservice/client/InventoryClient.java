package dev.anuradha.omsorderservice.client;

import dev.anuradha.omsorderservice.dto.ReserveStockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InventoryClient {

    private final RestTemplate restTemplate;

    @Value("${services.inventory.url}")
    private String inventoryServiceUrl;

    public boolean reserveStock(UUID productId, int quantity){
        String url = inventoryServiceUrl + "/api/inventory/reserve";

        ReserveStockRequest request = new ReserveStockRequest();
        request.setProductId(productId);
        request.setQuantity(quantity);

        Boolean response = restTemplate.postForObject(
                url,
                request,
                Boolean.class
        );

        return Boolean.TRUE.equals(response);
    }
}
